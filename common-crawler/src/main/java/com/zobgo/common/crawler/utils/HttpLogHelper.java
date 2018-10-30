package com.zogbo.common.utils;

import com.zogbo.common.utils.HttpMethod.HTTP_METHOD;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zongbo.zhang on 10/22/18.
 */
public class HttpLogHelper {
    private static final String HEADER_TEMPLATE = "[HTTP REQUEST HEADER]: %s";
    private static final String HTTP_METHOD_TEMPLATE = "[HTTP REQUEST METHOD]: %s";
    private static final String URL_TEMPLATE = "[HTTP REQUEST URL]: %s";
    private static final String LOCAL_IP_TEMPLATE = "[HTTP REQUEST LOCAL IP ADDRESS]: %S";
    private static final String CLIENT_IP_TEMPLATE = "[HTTP REQUEST CLIENT IP ADDRESS]: %s";
    private static final String QUERY_STRING_TEMPLATE = "[HTTP REQUEST QUERY STRING]: %s";
    private static final String BODY_TEMPLATE = "[HTTP REQUEST BODY]: %s";
    private static final String HTTP_RESPONSE_TEMPLATE = "[HTTP RESPONSE CONTENT]: %s";
    private static final String HTTP_RESPONSE_CODE_TEMPLATE = "[HTTP RESPONSE CODE]: %s";
    private static final String EMPTY_FLAG = "[EMPTY]";

    public HttpLogHelper() {
    }

    public static String logRequestOnClient(String header, HTTP_METHOD method, String url, String localIp, String qs, String body) {
        if (!StringUtils.isEmpty(header) && !StringUtils.isEmpty(url) && !StringUtils.isEmpty(localIp)) {
            return String.format("%s|%s|%s|%s|%s|%s", String.format("[HTTP REQUEST HEADER]: %s", header), String.format("[HTTP REQUEST METHOD]: %s", HttpMethod.getString(method)), String.format("[HTTP REQUEST LOCAL IP ADDRESS]: %S", localIp), String.format("[HTTP REQUEST URL]: %s", url), String.format("[HTTP REQUEST QUERY STRING]: %s", StringUtils.isEmpty(qs) ? "[EMPTY]" : qs), String.format("[HTTP REQUEST BODY]: %s", StringUtils.isEmpty(body) ? "[EMPTY]" : body));
        } else {
            throw new IllegalArgumentException("`header`, `url` and `clientId` should not be empty!");
        }
    }

    public static String logResponseOnClient(String header, HTTP_METHOD method, String url, String localIp, String qs, String reqBody, String code, String resBody) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("`code` should not be empty!");
        } else {
            return String.format("%s|%s|%s", logRequestOnClient(header, method, url, localIp, qs, reqBody), String.format("[HTTP RESPONSE CODE]: %s", code), String.format("[HTTP RESPONSE CONTENT]: %s", StringUtils.isEmpty(resBody) ? "[EMPTY]" : resBody));
        }
    }

    public static String logRequestOnServer(HTTP_METHOD method, String clientIp, String url, String qs, String body) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("`url` should not be empty!");
        } else {
            return String.format("%s|%s|%s|%s|%s", String.format("[HTTP REQUEST METHOD]: %s", HttpMethod.getString(method)), String.format("[HTTP REQUEST CLIENT IP ADDRESS]: %s", clientIp), String.format("[HTTP REQUEST URL]: %s", url), String.format("[HTTP REQUEST QUERY STRING]: %s", StringUtils.isEmpty(qs) ? "[EMPTY]" : qs), String.format("[HTTP REQUEST BODY]: %s", StringUtils.isEmpty(body) ? "[EMPTY]" : body));
        }
    }

    public static String logResponseOnServer(HTTP_METHOD method, String clientIp, String url, String qs, String reqBody, String code, String resBody) {
        return String.format("%s|%s|%s", logRequestOnServer(method, clientIp, url, qs, reqBody), String.format("[HTTP RESPONSE CODE]: %s", code), String.format("[HTTP RESPONSE CONTENT]: %s", StringUtils.isEmpty(resBody) ? "[EMPTY]" : resBody));
    }
}
