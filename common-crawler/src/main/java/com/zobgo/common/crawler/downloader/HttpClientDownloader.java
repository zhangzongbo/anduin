package com.zobgo.common.crawler.downloader;

import com.google.common.collect.Sets;


import com.zobgo.common.crawler.entity.ErrorResponse;
import com.zobgo.common.crawler.exception.*;
import com.zobgo.common.crawler.spider.Page;
import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Site;
import com.zobgo.common.crawler.spider.Task;
import com.zobgo.common.crawler.utils.HttpUtils;
import com.zobgo.common.crawler.utils.RedisPool;
import com.zogbo.common.utils.HttpConstant;
import com.zogbo.common.utils.UrlUtils;
import com.zogbo.common.utils.UserAgents;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by cw on 15-9-11.
 */
public class HttpClientDownloader extends AbstractDownloader {

    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(HttpClientDownloader.class);
    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        //httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
        return httpClient;
        //return httpClientGenerator.getClient(site);
    }

    @Override
    public Page download(Request request, Task task) {
        ErrorResponse errorResponse = new ErrorResponse();
        request.setStartDate();
        logger.info("start download " + request.getKey() + " url = " + request.getUrl() + " method=" + request.getMethod());
        Site site = request.getSite();
        Set<Integer> acceptStatCode;
        String charset = null;
        List<Header> headers = null;
        if (site != null) {
            acceptStatCode = site.getAcceptStatCode();
            charset = site.getCharset();
            headers = site.getHeaders();
            getUserAgent(headers, request);
        } else {
            acceptStatCode = Sets.newHashSet(200, HttpStatus.SC_MOVED_PERMANENTLY, HttpStatus.SC_MOVED_TEMPORARILY, HttpStatus.SC_SEE_OTHER, HttpStatus.SC_TEMPORARY_REDIRECT);
        }
        CloseableHttpResponse httpResponse = null;
        int statusCode = 0;
        try {
            HttpUriRequest httpUriRequest = getHttpUriRequest(request, site, headers);
            CloseableHttpClient httpClient = getHttpClient(site);
//            if ( request.getMethod().equalsIgnoreCase(HttpConstant.Method.GET) )
//            {
//                httpClient.getParams().setParameter("http.socket.timeout", new Integer(300000));
//                httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
//                httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
//            }
            httpResponse = httpClient.execute(httpUriRequest);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            logger.info("mobile = " + request.getKey() + " url = " + request.getUrl() + " statusCode=" + statusCode);
            request.putExtra(Request.STATUS_CODE, statusCode);
            if (statusAccept(acceptStatCode, statusCode)) {
                Page page = request.getResponseHandler().handleResponse(request, charset, httpResponse, task);
                onSuccess(request);
                request.setIsSuccess(true);
                return page;
            } else if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || statusCode == HttpStatus.SC_MOVED_TEMPORARILY
                    || statusCode == HttpStatus.SC_SEE_OTHER
                    || statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {//跳转
                return redirectNewUrl(request, httpResponse, task);
            } else {
                dealWithResponse(request, httpResponse);
                return null;
            }
        } catch (CrawlerTransportationException e) {
            onError(request);
            errorResponse.message = e.getMessage();
            request.setErrorResponse(errorResponse);
            request.setIsBlockException(true);
            request.setErrorException(e);
            try {
                logger.error(errorResponse.message, e);
                logger.error("mobile = " + request.getKey() + " statusCode = " + statusCode + " originHtml = " + (request.getPage() == null ? "unknown" : request.getPage().getOriginHtml()));
            } catch (Exception io) {
                logger.error("异常日志打印错误", io);
            }
            return null;
        } catch (BaseException e) {
            onError(request);
            errorResponse.message = e.getMessage();
            request.setErrorException(e);
            request.setErrorResponse(errorResponse);
            try {
                logger.error(errorResponse.message, e);
                logger.error("mobile = " + request.getKey() + " statusCode = " + statusCode + " originHtml = " + (request.getPage() == null ? "unknown" : request.getPage().getOriginHtml()));
            } catch (Exception io) {
                logger.error("异常日志打印错误", io);
            }
            return null;
        } catch (SocketTimeoutException | ConnectTimeoutException e) {
            onError(request);
            errorResponse.message = "下载超时 : download failed " + request.getKey() + " url = " + request.getUrl() + " method=" + request.getMethod();
            errorResponse.messageForClient = "运营商网站访问超时,请重试";
            request.setErrorResponse(errorResponse);
            request.setErrorException(new CrawlerNetworkException(errorResponse, e));
            logger.error(errorResponse.message, e);
            return null;
        } catch (Exception e) {
            onError(request);
            errorResponse.message = "download failed " + request.getKey() + " url = " + request.getUrl() + " method=" + request.getMethod();
            errorResponse.messageForClient = "服务不可用,我们正在紧急处理";
            request.setErrorResponse(errorResponse);
            request.setErrorException(new CrawlerReversionException(request.getKey(), errorResponse, e));
            logger.error(errorResponse.message, e);
            return null;
        } finally {
            logger.info("end download " + request.getKey() + " url = " + request.getUrl() + " statusCode=" + statusCode + " method=" + request.getMethod());
            request.setEndDate();
            request.putExtra(Request.STATUS_CODE, statusCode);
            try {
                if (httpResponse != null) {
                    //ensure the connection is released back to pool
                    EntityUtils.consume(httpResponse.getEntity());
                }
            } catch (IOException e) {
                errorResponse.message = "httpResponse consume failed " + request.getKey() + " url = " + request.getUrl();
                errorResponse.messageForClient = "服务异常";
                request.setErrorResponse(errorResponse);
                logger.error(errorResponse.message, e);
            }
        }
    }

    public Page redirectNewUrl(Request request, HttpResponse httpResponse, Task task) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        if (request.getExtra("count") == null) {
            request.putExtra("count", 0);
        }
        request.putExtra("count", (int) request.getExtra("count") + 1);
        if ((int) request.getExtra("count") > 15) {
            errorResponse.message = "登陆跳转次数过多，登陆失败";
            errorResponse.messageForClient = "登陆失败,请重试";
            request.setErrorResponse(errorResponse);
            throw new CrawlerReversionException(request.getKey(), errorResponse);
        }
        String location = httpResponse.getFirstHeader("Location") == null ? HttpUtils.getMacherForRegex(HttpUtils.URL_REGEX, getContent(request.getSite().getCharset(), httpResponse)) : httpResponse.getFirstHeader("Location").getValue();
        logger.info("location= " + location);

        if (location == null) {
            errorResponse.message = "登陆跳转location为空";
            errorResponse.messageForClient = "服务繁忙,登陆失败,请重试";
            request.setErrorResponse(errorResponse);
            throw new CrawlerBusinessException(errorResponse);
        }
        if (location.startsWith("/")) {
            location = HttpUtils.getHostWithProtocol(request.getUrl()) + location;
            logger.info("add host location= " + location);

        }
        List<Header> headers = new ArrayList<>();
        for (Header header : request.getSite().getHeaders()) {
            if (header.getName().equalsIgnoreCase("Referer")) {
                if (request.getSite().isNeedRefererWhenRedirect()) {
                    headers.add(new BasicHeader("Referer", request.getUrl()));
                } else {
                    headers.add(header);
                }
            } else if (header.getName().equalsIgnoreCase("host")) {
                headers.add(new BasicHeader(header.getName(), HttpUtils.getHost(location)));
            } else {
                headers.add(header);
            }
        }
        request.getSite().setHeaders(headers);
        request.setUrl(location);
        request.setMethod(HttpConstant.Method.GET);
        return download(request, task);

    }

    public void getUserAgent(List<Header> headers, Request request) {
        String flag = RedisPool.get("random_userAgent");
        if (flag != null) {
            for (Header header : headers) {
                if (header.getName().equalsIgnoreCase("User-Agent")) {
                    int key = 5;
                    try {
                        if (StringUtils.isNotEmpty(request.getKey())) {
                            key = (Math.abs(request.getKey().hashCode()) % 31) + 1;
                            logger.info(request.getKey() + " use the userAgent is " + key + " ########");
                        }
                    } catch (Exception e) {

                    }
                    headers.remove(header);
                    logger.info("Agent is ## " + UserAgents.getUserAgent(key));
                    headers.add(new BasicHeader("User-Agent", UserAgents.getUserAgent(key)));
                    break;
                }
            }
        }
    }

    public void dealWithResponse(Request request, HttpResponse response) {
        ErrorResponse errorResponse = new ErrorResponse();
        // Get response status code
        StatusLine statusLine = response.getStatusLine();
        int code = statusLine.getStatusCode();
//        if (555 == code){
//            logger.error(String.format("爬虫返回状态码:%s", code),"运营商服务器繁忙请稍后再试");
//            return;
//        }
        if (code >= 500) {
            errorResponse.message = String.format("爬虫返回状态码:%s", code);
            errorResponse.messageForClient = "运营商服务器繁忙请稍后再试";
            request.setErrorResponse(errorResponse);
            throw new CrawlerReversionException(request.getKey(), errorResponse);
        }
        if (code >= 400) {
            errorResponse.message = String.format("爬虫返回状态码:%s", code);
            errorResponse.messageForClient = "运营商服务器繁忙请稍后再试";
            request.setErrorResponse(errorResponse);
            throw new CrawlerReversionException(request.getKey(), errorResponse);
        }
        if (code != 200 && code < 300) {
            errorResponse.message = String.format("爬虫返回状态码:%s", code);
            errorResponse.messageForClient = "运营商系统异常，请联系技术支持。";
            request.setErrorResponse(errorResponse);
            throw new CrawlerDependencyException("","");
        }
    }

    @Override
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    protected boolean statusAccept(Set<Integer> acceptStatCode, int statusCode) {
        return acceptStatCode.contains(statusCode);
    }

    protected HttpUriRequest getHttpUriRequest(Request request, Site site, List<Header> headers) {
        RequestBuilder requestBuilder = selectRequestMethod(request).setUri(request.getUrl());
        if (headers != null) {
            for (Header header : headers) {
                requestBuilder.addHeader(header);
            }
        }
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(site.getTimeOut())
                .setSocketTimeout(site.getTimeOut())
                .setConnectTimeout(site.getTimeOut())
                .setCookieSpec(CookieSpecs.BEST_MATCH);
//                .setCookieSpec(CookieSpecs.STANDARD);

        requestBuilder.setConfig(requestConfigBuilder.build());
        return requestBuilder.build();
    }

    protected RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            RequestBuilder requestBuilder = RequestBuilder.post();
            NameValuePair[] nameValuePair = request.getSite().getNameValuePair();
            if (nameValuePair != null && nameValuePair.length > 0) {
                List<NameValuePair> nameValuePairList = new ArrayList<>();
                for (NameValuePair nameValuePair1 : request.getSite().getNameValuePair()) {
                    nameValuePairList.add(nameValuePair1);
                }
                try {
                    requestBuilder.setEntity(new UrlEncodedFormEntity(nameValuePairList, request.getSite().getCharset()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotEmpty(request.getSite().getContentType())) {
                if (StringUtils.isNotEmpty(request.getSite().getBody())) {
                    try {
                        requestBuilder.setEntity(new StringEntity(request.getSite().getBody(), request.getSite().getContentType(), request.getSite().getCharset()));
                    } catch (UnsupportedEncodingException e) {
                        logger.error("不支持此类型", e);
                    }
                }
            } else {
                if (StringUtils.isNotEmpty(request.getSite().getBody())) {
                    requestBuilder.setEntity(new StringEntity(request.getSite().getBody(), request.getSite().getCharset()));
                }
            }
            return requestBuilder;
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return RequestBuilder.put();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

    protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String charset;
        // charset
        // 1、encoding in http header Content-Type
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        // 2、charset in meta
        if (StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }

    public HttpClientDownloader setHttpClientGenerator(HttpClientGenerator httpClientGenerator) {
        this.httpClientGenerator = httpClientGenerator;
        return this;
    }
}
