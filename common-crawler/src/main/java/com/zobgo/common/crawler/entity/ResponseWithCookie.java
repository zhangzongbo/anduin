package com.zobgo.common.crawler.entity;

import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-09-01 18:05
 */
public class ResponseWithCookie {
    private String html;
    private List<Cookie> cookies = new ArrayList<>();

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

    public ResponseWithCookie() {}
}
