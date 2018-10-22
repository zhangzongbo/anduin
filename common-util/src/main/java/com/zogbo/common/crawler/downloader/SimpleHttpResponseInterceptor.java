package com.zogbo.common.crawler.downloader;


import com.zogbo.common.utils.HttpLogHelper;
import com.zogbo.common.utils.HttpMethod;
import com.zogbo.common.utils.exception.CrawlerBusinessException;


import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by cw on 15-9-15.
 */
public class SimpleHttpResponseInterceptor implements HttpResponseInterceptor {
    /**
     * Logger based on slf4j
     */
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpResponseInterceptor.class);

    private static SimpleHttpResponseInterceptor instance = new SimpleHttpResponseInterceptor();

    private static int RESPONSE_LENGTH_LIMIT = 2000;

    private SimpleHttpResponseInterceptor()
    {

    }
    public static SimpleHttpResponseInterceptor getInstance()
    {
        return instance;
    }
    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException
    {
        try {
            String url = null;
            String queryString = null;
            String requestBody = null;
            String header = null;
            HttpMethod.HTTP_METHOD httpMethod = null;
            InetAddress ip = InetAddress.getLocalHost();
            int code = response.getStatusLine().getStatusCode();
            String requestLog=String.valueOf(context.getAttribute("log"));
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);

            httpMethod = HttpMethod.HTTP_METHOD.valueOf("HTTP_" + request.getRequestLine().getMethod());
            header = Arrays.toString(request.getAllHeaders());
            try {
                url = ((HttpRequestWrapper) request).getTarget() + request.getRequestLine().getUri();
            }catch (Exception e){
                url = request.getRequestLine().getUri();
            }
            logger.info("========= response headers =============");
            for(Header header1 : response.getAllHeaders()){
                logger.info(header1.getName() +  " = " + header1.getValue());
            }
            logger.info("========= response headers =============");
            String responseBody = null;
            if (response.getFirstHeader("Content-Type") != null && response.getFirstHeader("Content-Type").getValue().contains("application/octet-stream")){//application/octet-stream{
                responseBody = "下载pdf成功";
            }else if (response.getFirstHeader("Content-Type") != null && response.getFirstHeader("Content-Type").getValue().contains("image/")){
                responseBody = "保存图片成功";
            } else if (code < 500) {
                // Replace entity as an repeatable entity
                BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());
                response.setEntity(entity);

                ContentType contentType = ContentType.getOrDefault(entity);

                Charset charset = contentType.getCharset();

                if (charset == null) {
                    charset = Consts.UTF_8;
                }
//                requestBody = "日志太多,暂时不打印返回结果";
                responseBody = EntityUtils.toString(entity, charset);
            }
            String startTime=context.getAttribute("startTime").toString();
            String responseTime=String.valueOf(System.currentTimeMillis()-Long.parseLong(startTime));
            String UserAgent=context.getAttribute("UserAgent").toString();
            if (StringUtils.isEmpty(responseBody)){
                logger.info("responseBody is empty");
            }else {
                logger.info(String.format("responseBody length is %s",responseBody.length()));
            }
            if (responseBody != null && responseBody.length() > RESPONSE_LENGTH_LIMIT){
                responseBody = String.format("[response body is too long only print body length is %s]",responseBody.length());
            }
            String responseLog = HttpLogHelper.logResponseOnClient(header, httpMethod, url, ip.getHostAddress(),
                    queryString, requestBody, String.valueOf(code), responseBody);
            logger.info("requestLog:"+requestLog+"[User-Agent]:"+UserAgent+"[responseTime]:"+responseTime+"(ms)"+responseLog);
        } catch (SocketTimeoutException e)
        {
            throw new CrawlerBusinessException("获取信息超时", "获取信息超时，请稍后重试", e);
        }
        catch (Exception e) {
            logger.error("爬虫日志异常", e);
//            throw new CrawlerTechnicalException("爬虫日志异常", "服务暂时不可用，请稍后重试", e);
        }
    }
}
