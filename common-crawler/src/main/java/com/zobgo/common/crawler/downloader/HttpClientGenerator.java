package com.zobgo.common.crawler.downloader;


import com.zobgo.common.crawler.exception.CrawlerDependencyException;
import com.zobgo.common.crawler.spider.Site;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by cw on 15-9-11.
 */
public class HttpClientGenerator {

    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(HttpClientGenerator.class);
    private PoolingHttpClientConnectionManager connectionManager;

    public HttpClientGenerator(){
        this("SSLv3");
    }

    public HttpClientGenerator(final String sslProtocol) {
        try {
            // Trust all cert not only self-signed
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                    // bypass
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                    // bypass
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            // Set SSLv3 for CMC connection
            SSLContext sslContext = SSLContext.getInstance(sslProtocol);
            sslContext.init(null, new TrustManager[]{tm}, null);

            // Build ssl context
            //SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

            // Pain socket factory for http
//            ConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();

            Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                    .build();
            connectionManager = new PoolingHttpClientConnectionManager(reg);
            connectionManager.setDefaultMaxPerRoute(100);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new CrawlerDependencyException("HttpClient初始化异常", "发生内部异常", e);
        }
//        try {
//            logger.info("start HttpClientGenerator");
//            Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
//                    .register("http", PlainConnectionSocketFactory.INSTANCE)
//                    .register("https", new SSLConnectionSocketFactory(SSLContext.getInstance("SSLv3"), NoopHostnameVerifier.INSTANCE))
//                    .build();
//            connectionManager = new PoolingHttpClientConnectionManager(reg);
//            connectionManager.setDefaultMaxPerRoute(100);
//            logger.info("end HttpClientGenerator");
//        }catch (NoSuchAlgorithmException e)
//        {
//            logger.error("初始化ConnectionSocketFactory失败",e);
//            Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
//                    .register("http", PlainConnectionSocketFactory.INSTANCE)
//                    .register("https", SSLConnectionSocketFactory.getSocketFactory())
//                    .build();
//            connectionManager = new PoolingHttpClientConnectionManager(reg);
//            connectionManager.setDefaultMaxPerRoute(100);
//        }
    }

    public HttpClientGenerator setPoolSize(int poolSize) {
        connectionManager.setMaxTotal(poolSize);
        return this;
    }

    public CloseableHttpClient getClient(Site site) {
        return generateClient(site);
    }

    private CloseableHttpClient generateClient(Site site) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connectionManager).addInterceptorFirst(SimpleHttpRequestInterceptor.getInstance()).addInterceptorLast(SimpleHttpResponseInterceptor.getInstance());
        if (site != null && site.getUserAgent() != null) {
            httpClientBuilder.setUserAgent(site.getUserAgent());
        } else {
            httpClientBuilder.setUserAgent("");
        }
        if (site == null || site.isUseGzip()) {
            httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {

                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws HttpException, IOException {
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                    }

                }
            });
        }else {
            httpClientBuilder.disableContentCompression();
        }
        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
//        if (site != null) {
//            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(site.getRetryTimes(), true));
//        }
        //set proxy
        generateRedirectStrategy(httpClientBuilder);
        generateCookie(httpClientBuilder, site);
        setProxy(httpClientBuilder,site);
        return httpClientBuilder.build();
    }

    private void setProxy(HttpClientBuilder httpClientBuilder,Site site){
        if (site != null && site.getHttpProxy() != null && site.getHttpProxy().validate()){
            HttpHost httpHost = new HttpHost(site.getHttpProxy().getIp(),site.getHttpProxy().getPort(),site.getHttpProxy().getScheme());
            DefaultProxyRoutePlanner defaultProxyRoutePlanner = new DefaultProxyRoutePlanner(httpHost);
            CredentialsProvider defaultCredentialsProvider = new BasicCredentialsProvider();
            defaultCredentialsProvider.setCredentials(
                    new AuthScope(site.getHttpProxy().getIp(),site.getHttpProxy().getPort()),
                    new UsernamePasswordCredentials(site.getHttpProxy().getUsername(),site.getHttpProxy().getPassword())
            );
            httpClientBuilder.setRoutePlanner(defaultProxyRoutePlanner);
            httpClientBuilder.setDefaultCredentialsProvider(defaultCredentialsProvider);
        }
    }
    private void generateRedirectStrategy(HttpClientBuilder httpClientBuilder)
    {
        httpClientBuilder.setRedirectStrategy(new DefaultRedirectStrategy()
        {
            public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException
            {
                return false;
            }
        });
    }
    private void generateCookie(HttpClientBuilder httpClientBuilder, Site site) {
        if ( site.getCookieStore() == null)
        {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie.setDomain(site.getDomain());
                cookieStore.addCookie(cookie);
            }
            for (Map.Entry<String, Map<String, String>> domainEntry : site.getAllCookies().entrySet()) {
                for (Map.Entry<String, String> cookieEntry : domainEntry.getValue().entrySet()) {
                    BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                    cookie.setDomain(domainEntry.getKey());
                    cookieStore.addCookie(cookie);
                }
            }
            site.setCookieStore(cookieStore);
        }
        httpClientBuilder.setDefaultCookieStore(site.getCookieStore());
    }

}
