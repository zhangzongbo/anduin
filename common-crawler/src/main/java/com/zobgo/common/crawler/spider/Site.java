package com.zobgo.common.crawler.spider;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;


import com.zobgo.common.crawler.proxy.HttpProxy;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by cw on 15-9-11.
 */
public class Site {
    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(Site.class);
    private String domain;

    private String userAgent;

    private Map<String, String> defaultCookies = new LinkedHashMap<String, String>();

    private Table<String, String, String> cookies = HashBasedTable.create();

    private String charset = "UTF-8";

    private String contentType;

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    /**
     * startUrls is the urls the crawler to start with.
     */
    private final AtomicLong tryTimes = new AtomicLong(0);

    private int sleepTime = 5000;

    private int retryTimes = 0;

    private int cycleRetryTimes = 0;

    private int retrySleepTime = 1000;

    private int timeOut = 25000;

    private static final Set<Integer> DEFAULT_STATUS_CODE_SET = new HashSet<Integer>();

    private Set<Integer> acceptStatCode = DEFAULT_STATUS_CODE_SET;

    private List<Header> headers = new ArrayList<>();

    private  NameValuePair[] nameValuePair;

    private String body;

//    private ProxyPool httpProxyPool;

//    private HttpHost httpProxy;

    private HttpProxy httpProxy;

    private boolean useGzip = true;

    private CookieStore cookieStore;

    private boolean isNeedRefererWhenRedirect = true;

    private boolean isNeedMessyCodeValidate = false;

    public boolean isNeedRefererWhenRedirect() {
        return isNeedRefererWhenRedirect;
    }

    public void setIsNeedRefererWhenRedirect(boolean isNeedRefererWhenRedirect) {
        this.isNeedRefererWhenRedirect = isNeedRefererWhenRedirect;
    }

    /**
     * @see
     * @deprecated
     */
    public static interface HeaderConst {

        public static final String REFERER = "Referer";
    }


    static {
        DEFAULT_STATUS_CODE_SET.add(200);
    }
    public long getTryTimes()
    {
        return tryTimes.get();
    }
    public long incrementAndGetTryTimes()
    {
        return tryTimes.incrementAndGet();
    }
    public NameValuePair[] getNameValuePair() {
        return nameValuePair;
    }

    public void setNameValuePair(NameValuePair[] nameValuePair) {
        this.nameValuePair = nameValuePair;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * new a Site
     *
     * @return new site
     */
    public static Site me() {
        return new Site();
    }

    /**
     * Add a cookie with domain {@link #getDomain()}
     *
     * @param name
     * @param value
     * @return this
     */
    public Site addCookie(String name, String value) {
        defaultCookies.put(name, value);
        return this;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    /**
     * Add a cookie with specific domain.
     *
     * @param domain
     * @param name
     * @param value
     * @return
     */
    public Site addCookie(String domain, String name, String value) {
        cookies.put(domain, name, value);
        return this;
    }

    /**
     * set user agent
     *
     * @param userAgent userAgent
     * @return this
     */
    public Site setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * get cookies
     *
     * @return get cookies
     */
    public Map<String, String> getCookies() {
        return defaultCookies;
    }

    /**
     * get cookies of all domains
     *
     * @return get cookies
     */
    public Map<String,Map<String, String>> getAllCookies() {
        return cookies.rowMap();
    }

    /**
     * get user agent
     *
     * @return user agent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * get domain
     *
     * @return get domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * set the domain of site.
     *
     * @param domain
     * @return this
     */
    public Site setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Set charset of page manually.<br>
     * When charset is not set or set to null, it can be auto detected by Http header.
     *
     * @param charset
     * @return this
     */
    public Site setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * get charset set manually
     *
     * @return charset
     */
    public String getCharset() {
        return charset;
    }

    public int getTimeOut() {
        return timeOut;
    }

    /**
     * set timeout for downloader in ms
     *
     * @param timeOut
     */
    public Site setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    /**
     * Set acceptStatCode.<br>
     * When status code of http response is in acceptStatCodes, it will be processed.<br>
     * {200} by default.<br>
     * It is not necessarily to be set.<br>
     *
     * @param acceptStatCode
     * @return this
     */
    public Site setAcceptStatCode(Set<Integer> acceptStatCode) {
        this.acceptStatCode = acceptStatCode;
        return this;
    }

    /**
     * get acceptStatCode
     *
     * @return acceptStatCode
     */
    public Set<Integer> getAcceptStatCode() {
        return acceptStatCode;
    }

    /**
     * Set the interval between the processing of two pages.<br>
     * Time unit is micro seconds.<br>
     *
     * @param sleepTime
     * @return this
     */
    public Site setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    /**
     * Get the interval between the processing of two pages.<br>
     * Time unit is micro seconds.<br>
     *
     * @return the interval between the processing of two pages,
     */
    public int getSleepTime() {
        return sleepTime;
    }

    /**
     * Get retry times immediately when download fail, 0 by default.<br>
     *
     * @return retry times when download fail
     */
    public int getRetryTimes() {
        return retryTimes;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    /**
     *
     * @param header
     * @return
     */
    public Site addHeader(Header header) {
        headers.add(header);
        return this;
    }

    /**
     * Set retry times when download fail, 0 by default.<br>
     *
     * @return this
     */
    public Site setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    /**
     * When cycleRetryTimes is more than 0, it will add back to scheduler and try download again. <br>
     *
     * @return retry times when download fail
     */
    public int getCycleRetryTimes() {
        return cycleRetryTimes;
    }

    /**
     * Set cycleRetryTimes times when download fail, 0 by default. <br>
     *
     * @return this
     */
    public Site setCycleRetryTimes(int cycleRetryTimes) {
        this.cycleRetryTimes = cycleRetryTimes;
        return this;
    }

//    public HttpHost getHttpProxy() {
//        return httpProxy;
//    }
//
//    /**
//     * set up httpProxy for this site
//     *
//     * @param httpProxy
//     * @return
//     */
//    public Site setHttpProxy(HttpHost httpProxy) {
//        this.httpProxy = httpProxy;
//        return this;
//    }
//
//    /**
//     * Set httpProxyPool, String[0]:ip, String[1]:port <br>
//     *
//     * @return this
//     */
//    public Site setHttpProxyPool(List<String[]> httpProxyList) {
//        this.httpProxyPool=new ProxyPool(httpProxyList);
//        return this;
//    }
//
//    public Site enableHttpProxyPool() {
//        this.httpProxyPool=new ProxyPool();
//        return this;
//    }
//
//    public ProxyPool getHttpProxyPool() {
//        return httpProxyPool;
//    }
//
//    public HttpHost getHttpProxyFromPool() {
//        return httpProxyPool.getProxy();
//    }
//
//    public void returnHttpProxyToPool(HttpHost proxy,int statusCode) {
//        httpProxyPool.returnProxy(proxy,statusCode);
//    }
//
//    public Site setProxyReuseInterval(int reuseInterval) {
//        this.httpProxyPool.setReuseInterval(reuseInterval);
//        return this;
//    }

    public boolean isUseGzip() {
        return useGzip;
    }

    public int getRetrySleepTime() {
        return retrySleepTime;
    }

    /**
     * Set retry sleep times when download fail, 1000 by default. <br>
     *
     * @param retrySleepTime
     */
    public Site setRetrySleepTime(int retrySleepTime) {
        this.retrySleepTime = retrySleepTime;
        return this;
    }

    /**
     * Whether use gzip. <br>
     * Default is true, you can set it to false to disable gzip.
     *
     * @param useGzip
     * @return
     */
    public Site setUseGzip(boolean useGzip) {
        this.useGzip = useGzip;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (cycleRetryTimes != site.cycleRetryTimes) return false;
        if (retryTimes != site.retryTimes) return false;
        if (sleepTime != site.sleepTime) return false;
        if (timeOut != site.timeOut) return false;
        if (acceptStatCode != null ? !acceptStatCode.equals(site.acceptStatCode) : site.acceptStatCode != null)
            return false;
        if (charset != null ? !charset.equals(site.charset) : site.charset != null) return false;
        if (defaultCookies != null ? !defaultCookies.equals(site.defaultCookies) : site.defaultCookies != null)
            return false;
        if (domain != null ? !domain.equals(site.domain) : site.domain != null) return false;
        if (headers != null ? !headers.equals(site.headers) : site.headers != null) return false;
        if (userAgent != null ? !userAgent.equals(site.userAgent) : site.userAgent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (defaultCookies != null ? defaultCookies.hashCode() : 0);
        result = 31 * result + (charset != null ? charset.hashCode() : 0);
        result = 31 * result + sleepTime;
        result = 31 * result + retryTimes;
        result = 31 * result + cycleRetryTimes;
        result = 31 * result + timeOut;
        result = 31 * result + (acceptStatCode != null ? acceptStatCode.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Site{" +
                "domain='" + domain + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cookies=" + defaultCookies +
                ", charset='" + charset + '\'' +
                ", sleepTime=" + sleepTime +
                ", retryTimes=" + retryTimes +
                ", cycleRetryTimes=" + cycleRetryTimes +
                ", timeOut=" + timeOut +
                ", acceptStatCode=" + acceptStatCode +
                ", headers=" + headers +
                '}';
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public boolean isNeedMessyCodeValidate() {
        return isNeedMessyCodeValidate;
    }

    public void setNeedMessyCodeValidate(boolean needMessyCodeValidate) {
        isNeedMessyCodeValidate = needMessyCodeValidate;
    }

    public HttpProxy getHttpProxy() {
        return httpProxy;
    }

    public void setHttpProxy(HttpProxy httpProxy) {
        this.httpProxy = httpProxy;
    }
}
