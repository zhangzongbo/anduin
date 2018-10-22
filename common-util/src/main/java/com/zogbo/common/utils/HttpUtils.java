package com.zogbo.common.utils;


import com.google.common.collect.Sets;
import com.alibaba.fastjson.JSONObject;
import com.zogbo.common.crawler.spider.Request;
import com.zogbo.common.crawler.spider.Site;
import com.zogbo.common.utils.exception.CrawlerBusinessException;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * Http utils
 */
public class HttpUtils {
    /**
     * Logger based on slf4j
     */
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static Set redirectCode = Sets.newHashSet(200, HttpStatus.SC_MOVED_PERMANENTLY, HttpStatus.SC_MOVED_TEMPORARILY, HttpStatus.SC_SEE_OTHER, HttpStatus.SC_TEMPORARY_REDIRECT);

    public static String URL_REGEX = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";

    public static String getHost(String url)
    {	try
        {
            Pattern p = Pattern.compile("(http://|https://)?([^/]*)",Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(url);
            return m.find()?m.group(2):url;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static String getHostWithProtocol(String url)
    {	try
        {
            Pattern p = Pattern.compile("(http://|https://)?[^/]*",Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(url);
            return m.find()?m.group(0):url;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static NameValuePair[] jsonToNameValuePair(JSONObject json)
    {
        if ( json == null ) return null;
        NameValuePair[] nameValuePairs = new NameValuePair[json.size()];
        int index = 0;
        for (String key:json.keySet())
        {
            nameValuePairs[index++] = new BasicNameValuePair(key,json.getString(key));
        }
        return nameValuePairs;
    }
    public static String getMacherForRegex(String regex,String data)
    {
        try
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            if (matcher.find())
            {
                return matcher.group(0);
            }
            return null;
        }catch (Exception e)
        {
            return null;
        }
    }

    /**
     *
     * @param regex
     * @param data
     * @param index 第几个匹配
     * @return
     */
    public static String getMacherForRegex(String regex,String data,int index)
    {
        try
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            if (matcher.matches())
            {
                return matcher.group(index);
            }
            return null;
        }catch (Exception e)
        {
            return null;
        }
    }
    public static String getValueFromCookieStore(String name,CookieStore cookieStore)
    {
        for(Cookie cookie:cookieStore.getCookies())
        {
            if (cookie.getName().equalsIgnoreCase(name))
            {
                return cookie.getValue();
            }
        }
        return null;
    }
    public static String getRealIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
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

    public static InputStream getInputStreamFromResponse(HttpResponse response){
        HttpEntity gzipentity=response.getEntity();
        Header[] headers = response.getHeaders("Content-Encoding");
        System.out.println("header size:"+headers.length);
        for(Header h:headers){
            if(h.getValue().contains("gzip")){
                gzipentity=new GzipDecompressingEntity(gzipentity);
                break;
            }
        }
        InputStream in=null;
        try {
            in = gzipentity.getContent();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  in;
    }

    public static String getStringFromResponse(HttpResponse response) {
        String charset = "utf-8";
        String html = null;
        try {
            HttpEntity entity = response.getEntity(); // 获得响应实体
            if (entity != null) {
                if (entity.getContentEncoding() != null && entity.getContentEncoding().getValue().toLowerCase().indexOf("gzip") > -1) {
                    html = EntityUtils.toString(new GzipDecompressingEntity(entity));
                    html = convert(html);
                } else
                    html = EntityUtils.toString(entity, charset);// 获取头信息，进行得到网页编码
                //html = EntityUtils.toString(entity, getCharSetByBody(html, charset));// 对返回的html代码进行解析转码
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

//    public static String getStringFromResponse(HttpResponse response, String charset) {
//        String html = null;
//        try {
//            HttpEntity entity = response.getEntity(); // 获得响应实体
//            if (entity != null) {
//                if (entity.getContentEncoding() != null && entity.getContentEncoding().getValue().toLowerCase().indexOf("gzip") > -1) {
//                    html = EntityUtils.toString(new GzipDecompressingEntity(entity), charset);
//                    html = convert(html);
//                } else
//                    html = EntityUtils.toString(entity, charset);// 获取头信息，进行得到网页编码
//                //html = EntityUtils.toString(entity, getCharSetByBody(html, charset));// 对返回的html代码进行解析转码
//            }
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return html;
//    }

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        sb.append(utfString.substring(pos));
        return sb.toString();
    }

    public static List<Cookie> getResponseCookies(HttpResponse response) {
        List<Cookie> cookies = null;
        Header[] hds = response.getAllHeaders();
        if (hds != null && hds.length > 0) {
            for (int i = 0; i < hds.length; i++) {
                logger.info(hds[i].getName() + "  =   " + hds[i].getValue());
                if (hds[i].getName().equalsIgnoreCase("Set-Cookie"))
                {
                    if (cookies == null) {
                        cookies = new ArrayList<Cookie>();
                    }
                    String cookiestring[]=hds[i].getValue().split(";");
					String ss[]=cookiestring[0].split("=",2);
					String cookiename="";
					String cookievalue="";
					Cookie cookie=null;
					if (ss.length>1){
						cookiename=ss[0];
						cookievalue=ss[1];
						cookie=new BasicClientCookie(cookiename,cookievalue);
						cookies.add(cookie);
					}
                }
            }
        }
        return cookies;
    }
    public static String getResponseCookies2String(HttpResponse response){
        StringBuffer cookies=new StringBuffer();
        Header[] hds=response.getAllHeaders();
        boolean start = true;
        if(hds!=null && hds.length>0){
            for(int i=0;i<hds.length;i++){
                if(hds[i].getName().equalsIgnoreCase("Set-Cookie"))
                {
                    if (!start)
                    {
                        cookies.append(";");
                    }
                    start = false;
                    cookies.append(hds[i].getValue());
                }
            }
        }
        return cookies.toString();
    }

//    public static InputStream getInputStreamFromResponse(HttpResponse response) {
//        HttpEntity gzipentity = response.getEntity();
//        Header[] headers = response.getHeaders("Content-Encoding");
//        System.out.println("header size:" + headers.length);
//        for (Header h : headers) {
//            if (h.getValue().contains("gzip")) {
//                gzipentity = new GzipDecompressingEntity(gzipentity);
//                break;
//            }
//        }
//        InputStream in = null;
//        try {
//            in = gzipentity.getContent();
//        } catch (IllegalStateException | IOException e) {
//            //throw new Crawler
//        }
//        return in;
//    }

    public static StringBuffer getCookieValues(List<Cookie> cookies) {
        if (cookies == null)
        {
            return new StringBuffer();
        }
        StringBuffer sb = new StringBuffer();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (!"".equals(sb.toString())) {
                    sb.append(" ");
                }
                sb.append(c.getName());
                sb.append("=");
                sb.append(c.getValue() + ";");
            }
        }
        return sb;
    }

    /**
     * params :
     * cookies数组
     * return : cookies数组组成的字符串
     */
    public static String setCookie2String(List<Cookie> cookies){
        StringBuilder builder=null;
        if(cookies!=null && cookies.size()>0){
            builder=new StringBuilder();
            for(int j=0;j<cookies.size();j++){
                Cookie c=cookies.get(j);
                builder.append(c.getName()+"="+c.getValue());
                if(j!=cookies.size()-1)
                    builder.append("; ");
            }
            return builder.toString();
        }
        return null;
    }

    public static Request getRequestFromReturnForm(String formId, String html)
    {
        if ( formId == null || html == null )
        {
            throw new CrawlerBusinessException("formId或html为空","服务异常,请重试");
        }
        Document document;
        Element form;
        document = Jsoup.parse(html);
        if (document == null)
        {
            throw new CrawlerBusinessException("解析html失败","服务异常,请重试");
        }
        form = document.getElementById(formId);
        if ( form == null ) {
            Elements elements = document.getElementsByAttributeValue("name", formId);
            if (elements != null && elements.size() > 0) {
                form = elements.get(0);
            }
            if (form == null) {
                throw new CrawlerBusinessException("解析html失败,html不包含表单" + formId, "服务异常,请重试");
            }
        }
        Request request = new Request(form.attr("action"));
        request.setMethod(form.attr("method") == null ? HttpConstant.Method.GET:form.attr("method").toUpperCase());
        Site site = new Site();
        Elements data = form.select("input");
        if ( data == null || data.size() == 0)
        {
            site.setBody("");
        }
        else
        {
            NameValuePair[] nameValuePairs = new NameValuePair[data.size()];
            for (int i = 0; i < data.size(); ++i)
            {
                nameValuePairs[i] = new BasicNameValuePair(data.get(i).attr("name"),data.get(i).val());
            }
            site.setNameValuePair(nameValuePairs);
        }
        request.setSite(site);
        return request;
    }
}
