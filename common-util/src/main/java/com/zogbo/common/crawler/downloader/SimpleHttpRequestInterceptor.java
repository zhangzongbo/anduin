package com.zogbo.common.crawler.downloader;

import com.zogbo.common.utils.HttpLogHelper;
import com.zogbo.common.utils.HttpMethod;
import com.zogbo.common.utils.exception.CrawlerTechnicalException;


import org.apache.http.*;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by cw on 15-9-15.
 */
public class SimpleHttpRequestInterceptor implements HttpRequestInterceptor {
    /**
     * Logger based on slf4j
     */
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpRequestInterceptor.class);

    private static SimpleHttpRequestInterceptor instance = new SimpleHttpRequestInterceptor();

    private SimpleHttpRequestInterceptor()
    {

    }
    public static SimpleHttpRequestInterceptor getInstance()
    {
        return instance;
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        try {
            String url = null;
            String queryString = null;
            String requestBody = null;
            String header = Arrays.toString(httpRequest.getAllHeaders());
            HttpMethod.HTTP_METHOD httpMethod = HttpMethod.HTTP_METHOD.valueOf("HTTP_" + httpRequest.getRequestLine().getMethod());
            InetAddress ip = InetAddress.getLocalHost();
            String UserAgent = "null";
            if (header.contains("User-Agent")){
                UserAgent=httpRequest.getFirstHeader("User-Agent").getValue();
            }
//            logger.info(UserAgent);


            if (httpRequest instanceof HttpEntityEnclosingRequest) {
                HttpEntityEnclosingRequest request = (HttpEntityEnclosingRequest) httpRequest;

                if (request instanceof HttpRequestWrapper) {
                    url = ((HttpRequestWrapper) request).getTarget() + httpRequest.getRequestLine().getUri();
                } else {
                    url = "";
                }

                HttpEntity rqEntity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();

                // Replace entity as an repeatable entity
                BufferedHttpEntity entity = new BufferedHttpEntity(rqEntity);
                request.setEntity(entity);

                ContentType contentType = ContentType.getOrDefault(entity);

                Charset charset = contentType.getCharset();

                if (charset == null) {
                    charset = Consts.UTF_8;
                }

                requestBody = EntityUtils.toString(entity, charset);

            } else if (httpRequest instanceof HttpUriRequest) {
                HttpUriRequest request = (HttpUriRequest) httpRequest;
                httpMethod = HttpMethod.HTTP_METHOD.valueOf("HTTP_" + request.getMethod());
                url = ((HttpRequestWrapper) request).getTarget() + httpRequest.getRequestLine().getUri();
            }

            String requestLog = HttpLogHelper.logRequestOnClient(header, httpMethod, url, ip.getHostAddress(), queryString, requestBody);
            logger.info(requestLog);
            String log="[header]:"+header+"[queryString]:"+queryString+"[requestBody]:"+requestBody+"[url]:"+url;
            httpContext.setAttribute("log",log);
            httpContext.setAttribute("startTime",System.currentTimeMillis());
            httpContext.setAttribute("UserAgent",UserAgent);
        } catch (Exception e) {
            throw new CrawlerTechnicalException("爬虫日志异常", "服务暂时不可用，请稍后重试", e);
        }
    }
}


