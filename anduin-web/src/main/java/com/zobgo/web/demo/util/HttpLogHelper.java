package com.zobgo.web.demo.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by chenwen on 16/9/21.
 */
public class HttpLogHelper {
    private static final String HEADER_TEMPLATE = "[HTTP REQUEST HEADER]: %s";
    private static final String HEADER_RESPONSE_TEMPLATE = "[HTTP RESPONSE HEADER]: %s";
    private static final String HTTP_METHOD_TEMPLATE = "[HTTP REQUEST METHOD]: %s";
    private static final String URL_TEMPLATE = "[HTTP REQUEST URL]: %s";
    private static final String IP_TEMPLATE = "[HTTP REQUEST IP ADDRESS]: %s";
    private static final String QUERY_STRING_TEMPLATE = "[HTTP REQUEST QUERY STRING]: %s";
    private static final String BODY_TEMPLATE = "[HTTP REQUEST BODY]: %s";
    private static final String HTTP_RESPONSE_TEMPLATE = "[HTTP RESPONSE CONTENT]: %s";
    private static final String HTTP_RESPONSE_CODE_TEMPLATE = "[HTTP RESPONSE CODE]: %s";
    private static final String HTTP_RESPONSE_BODY_SIZE_TEMPLATE = "[HTTP RESPONSE BODY SIZE]: %s";
    private static final String HTTP_COST_TIME = "[HTTP COST TIME]: %s";
    private static final String EMPTY_FLAG = "[EMPTY]";

    public HttpLogHelper() {
    }

    public static String logRequestOnClient(String header, HttpMethod.HTTP_METHOD method, String url, String localIp, String qs, String body) {
        if(!StringUtils.isEmpty(header) && !StringUtils.isEmpty(url) && !StringUtils.isEmpty(localIp)) {
            return String.format("%s | %s | %s | %s | %s | %s", String.format(HEADER_TEMPLATE, header), String.format(HTTP_METHOD_TEMPLATE, HttpMethod.getString(method)), String.format(IP_TEMPLATE, localIp), String.format(URL_TEMPLATE, url), String.format(QUERY_STRING_TEMPLATE, StringUtils.isEmpty(qs)?EMPTY_FLAG:qs), String.format(BODY_TEMPLATE, StringUtils.isEmpty(body)?EMPTY_FLAG:body));
        } else {
            throw new IllegalArgumentException("`header`, `url` and `clientId` should not be empty!");
        }
    }

    public static String logResponseOnClient(String header, String requestHeader, HttpMethod.HTTP_METHOD method, String url, String localIp, String qs, String reqBody, String code, long resBodySize, long costTime) {
        if(StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("`code` should not be empty!");
        } else {
            return String.format("%s | %s | %s | %s | %s", String.format(HEADER_RESPONSE_TEMPLATE,header),
                    logRequestOnClient(requestHeader, method, url, localIp, qs, reqBody),
                    String.format(HTTP_RESPONSE_CODE_TEMPLATE, code),
                    String.format(HTTP_RESPONSE_BODY_SIZE_TEMPLATE, resBodySize),
                    String.format(HTTP_COST_TIME,(costTime*1.0 / 1000.0)));
        }
    }

}
