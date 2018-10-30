package com.zobgo.common.crawler.proxy;

import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Site;
import com.zobgo.common.crawler.spider.Spider;
import com.zobgo.common.crawler.utils.HttpProxyUtil;
import com.zogbo.common.utils.HttpConstant;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by cw on 15-11-26.
 */
public class HttpProxy implements Serializable {

    private Logger logger = LoggerFactory.getLogger(HttpProxy.class);

    private String scheme = "http";

    private String username;

    private String password;

    private String ip;

    private int port;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public boolean validate(){
        if (       StringUtils.isEmpty(password)
                || StringUtils.isEmpty(username)
                || StringUtils.isEmpty(ip)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HttpProxy{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    public static void main(String[] args){
        Request request1 = new Request("http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=ip&rsv_pq=ac81794a00031a4c&rsv_t=6101sTzsSyaXUaAccx5Cf2MxtCedmHEEp1oImqajNfc913tNJEVxXX6pBxI&rsv_enter=1&rsv_sug3=2&rsv_sug1=1&rsv_sug2=0&inputT=466&rsv_sug4=466");
        Site site = new Site();
        site.setHttpProxy(HttpProxyUtil.getHttpProxy(request1.getKey()));
        request1.setSite(site);
        request1.setMethod(HttpConstant.Method.GET);
        Spider.create().addRequest(request1).run();
        System.out.println(request1.getPage().getOriginHtml());
    }
}
