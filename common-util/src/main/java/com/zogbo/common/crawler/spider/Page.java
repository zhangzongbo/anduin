package com.zogbo.common.crawler.spider;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.selector.Html;

/**
 * Created by cw on 15-9-11.
 */
public class Page {

    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(Page.class);
    private int statusCode;
    //原始返回值
    private String originHtml;
    //归一化后的返回值
    private String html;

    public Html getCorehtml() {

        return corehtml;
    }

    public void setCorehtml(Html corehtml) {
        this.corehtml = corehtml;
    }

    //源码中的html，支持xpath
    private Html corehtml;
    //组装的返回结果
    private String result;
    //
    private HttpResponse httpResponse;

    private List<Cookie> cookies = new ArrayList<>();

    private Header[] headers;

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {

        if (cookies != null) {
            this.cookies.clear();
            this.cookies.addAll(cookies);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getOriginHtml() {
        return originHtml;
    }

    public void setOriginHtml(String originHtml) {
        this.originHtml = originHtml;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
    public String getResult()
    {
        return result;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}