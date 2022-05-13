package com.zobgo.web.demo.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.zobgo.web.demo.exception.BaseException;
import com.zobgo.web.demo.exception.BasicWebBasicCodeEnum;
import com.zobgo.web.demo.exception.WebException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Http utils
 */
public class HttpUtils {
    /**
     * Logger based on slf4j
     */

    private static Validator validator = Validation
            .buildDefaultValidatorFactory().getValidator();
    private static Logger logger = Logger.getLogger(HttpUtils.class);

    public static String getRealIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-UserLoginDTO-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-UserLoginDTO-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

//        logger.info("ip is " + ip);
        return ip;
    }

    public static String getRequestBodyAsJson(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        } catch (IOException e) {
            throw new WebException(BasicWebBasicCodeEnum.ARGUMENT_PARSE_ERROR,e);
        }
    }

    public static String getQueryStringAsJson(HttpServletRequest request) {
        try {
            String queryStr = request.getQueryString();
            if (StringUtils.isNotEmpty(queryStr)) {
                return getRequestBody(URLDecoder.decode(request.getQueryString(), request.getCharacterEncoding()));
            } else {
                return "{}";
            }
        } catch (UnsupportedEncodingException e) {
            throw new WebException(BasicWebBasicCodeEnum.ARGUMENT_PARSE_ERROR,e);
        }
    }

    public static <T> T getModelFromQueryString(String queryStringJson, Class<T> type){
        T result = new Gson().fromJson(queryStringJson, type);
        validate(result, type);
        return result;
    }

    public static <T> T getModelFromQueryString(HttpServletRequest request, Class<T> type){
        T result = new Gson().fromJson(getQueryStringAsJson(request), type);
        validate(result, type);
        return result;
    }

    public static <T> T getModelFromRequest(HttpServletRequest request, Class<T> type){
        T result;
        try {
            String requestBody = null;
            if (request.getMethod().equalsIgnoreCase(HttpMethod.HTTP_METHOD.HTTP_POST.getString())) {
                if (request.getContentType().startsWith(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())){
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    JSONObject json = new JSONObject();
                    for(String key : parameterMap.keySet()){
                        String[] value = parameterMap.get(key);
                        if (value != null && value.length > 0) {
                            json.put(key, parameterMap.get(key)[0]);
                        }
                    }
                    requestBody = json.toJSONString();
                }else {
                    requestBody = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(requestBody) && !requestBody.startsWith("{")) {
                        requestBody = getRequestBody(requestBody);
                    }
                }
            } else if (request.getMethod().equalsIgnoreCase(HttpMethod.HTTP_METHOD.HTTP_GET.getString())) {
//                if (StringUtils.isNotEmpty(request.getQueryString())) {
                    requestBody = getRequestBody(URLDecoder.decode(request.getQueryString(), request.getCharacterEncoding()));
//                }else {
//                    return null;
//                }
            }
            result = JSON.parseObject(requestBody, type);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new WebException(BasicWebBasicCodeEnum.ARGUMENT_PARSE_ERROR,e);
        }
        validate(result, type);
        return result;
    }

    public static <T> void validate(T t, Class<?> type) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.size() > 0) {
            int index = 0;
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                String msg = constraintViolation.getMessage();
                if (msg != null && StringUtils.isNotEmpty(msg)){
                    message.append(msg);
                } else {
                    message.append("字段不合法");
                }
                logger.error(String.format("{ %s (%s): 字段不合法 }", constraintViolation.getPropertyPath(), constraintViolation.getInvalidValue()));
                if (index != constraintViolations.size() - 1) {
                    message.append(" , ");
                }
                index++;
            }
            throw new WebException(BasicWebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR.getCode(),message.toString());
        }
    }

    public static String getReceiveParam(HttpServletRequest request) {
        BufferedReader bufferedReader = null;
        StringBuilder data = new StringBuilder();
        try {
            request.setCharacterEncoding("UTF-8");
            bufferedReader = request.getReader();
            if (bufferedReader == null) {
                return null;
            }
            String line;
            while ((line = bufferedReader.readLine())!=null){
                data.append(line);
            }
        } catch (Exception e) {
            logger.error("ͨ解析参数异常", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ie) {
                logger.error("ͨ解析参数异常", ie);
            }
        }
        return data.toString();
    }

    public static String getRequestBody(String requestBody) {
        if (StringUtils.isEmpty(requestBody)) {
            throw new WebException(BasicWebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR);
        }
        Pattern pattern = Pattern.compile("(req|param)=\\{(.+:.+)+\\}");
        Matcher matcher = pattern.matcher(requestBody);
        if (matcher.find()) {
            return requestBody.replaceFirst("(req|param)=","");
        }

        JSONObject requestBodyJSON = null;
        matcher = Pattern.compile("(([a-zA-Z0-9_]+)=([^&]+))&?+").matcher(requestBody);
        while (matcher.find()) {
            if (requestBodyJSON == null) {
                requestBodyJSON = new JSONObject();
            }
            String key = matcher.group(2);
            String value = matcher.group(3);
            if (value.startsWith("[") && value.endsWith("]")){
                requestBodyJSON.put(key, JSONArray.parseArray(value));
            }else if (value.startsWith("{") && value.endsWith("}")){
                requestBodyJSON.put(key, JSONObject.parseObject(value));
            }else {
                requestBodyJSON.put(key, value);
            }
        }
        if (requestBodyJSON == null) {
            logger.error(requestBody);
            throw new WebException(BasicWebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR);
        }
        return JSON.toJSONString(requestBodyJSON);
    }

    public static void main(String[] args) {
        String requestBody = "timestamp=1234567891023&signature=A82F684D47FE99528209B6EC0A56F1C0";
        JSONObject requestBodyJSON = null;
        Matcher matcher = Pattern.compile("(([a-zA-Z0-9_]+)=([^&]+))&?+").matcher(requestBody);
        while (matcher.find()) {
            if (requestBodyJSON == null) {
                requestBodyJSON = new JSONObject();
            }
            requestBodyJSON.put(matcher.group(2), matcher.group(3));
        }
        if (requestBodyJSON == null) {
            logger.error(requestBody);
            throw new WebException(BasicWebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR);
        }
        logger.error(JSON.toJSONString(requestBodyJSON));
    }
}
