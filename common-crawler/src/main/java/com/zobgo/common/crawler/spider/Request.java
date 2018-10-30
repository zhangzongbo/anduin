package com.zobgo.common.crawler.spider;


import com.zobgo.common.crawler.entity.ErrorResponse;
import com.zobgo.common.crawler.exception.BaseException;
import com.zobgo.common.crawler.result.responsehandler.AbstractResponseHandler;
import com.zobgo.common.crawler.result.responsehandler.DefaultResponseHandler;
import com.zobgo.common.crawler.result.template.TargetTemplateProducter;
import com.zobgo.common.crawler.rules.DefaultRetryRequestRule;
import com.zobgo.common.crawler.rules.IRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cw on 15-9-11.
 */
public class Request implements Serializable {
    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(Request.class);
    private String key;
    private String startDate;
    private String endDate;
    private boolean isSuccess = false;
    private ErrorResponse errorResponse = null;
    private BaseException errorException = null;
    private IRule retryRule = new DefaultRetryRequestRule();
    protected long priority;
    protected String url;
    protected String method;
    protected Site site;
    protected Page page;
    protected String uuid;
    protected String cookie = "";
    //protected int waitTimeWhenOneThread = 1000;
    //protected String mobile;
    protected TargetTemplateProducter targetTemplateProducter;
    protected AbstractResponseHandler responseHandler = new DefaultResponseHandler();
    protected Map<String, Object> extras;
    public static final String CYCLE_TRIED_TIMES = "_cycle_tried_times";
    public static final String STATUS_CODE = "_statusCode";
    public static final String FILENAME_EXTENSION = "filename_extension";
    public static final String PROXY = "proxy";
    private boolean isBlockException  = false;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public TargetTemplateProducter getTargetTemplateProducter() {
        return targetTemplateProducter;
    }

    public void setTargetTemplateProducter(TargetTemplateProducter targetTemplateProducter) {
        this.targetTemplateProducter = targetTemplateProducter;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        if ( this.errorResponse == null) {
            this.errorResponse = errorResponse;
        }
    }

    public Exception getErrorException() {
        return errorException;
    }

    public void setErrorException(BaseException errorException) {
        this.errorException = errorException;
    }
//    public int getWaitTimeWhenOneThread() {
//        return waitTimeWhenOneThread;
//    }
//
//    public void setWaitTimeWhenOneThread(int waitTimeWhenOneThread) {
//        this.waitTimeWhenOneThread = waitTimeWhenOneThread;
//    }

    public boolean isBlockException() {
        return isBlockException;
    }

    public void setIsBlockException(boolean isBlockException) {
        this.isBlockException = isBlockException;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public IRule getRetryRule() {
        return retryRule;
    }

    public boolean isNeedRetry()
    {
        try {
            return (boolean) retryRule.isConformRule(this);
        }catch (Exception e){
            logger.error("重试规则出错",e);
        }
        return false;
    }

    public void setRetryRule(IRule retryRule) {
        this.retryRule = retryRule;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }

    public static final String CAPTCHA_URL="captcha_url";

    public Request(String url)
    {
        this.url = url.replace(" ", "%20").replaceFirst(":443", "");
    }

    public Request(String url, String cookie) {this.url = url;this.cookie = cookie;}

    public void init()
    {

    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public AbstractResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(AbstractResponseHandler abstractResponseHandler) {
        this.responseHandler = abstractResponseHandler;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startDate = format.format(Calendar.getInstance().getTime());
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startDate = format.format(Calendar.getInstance().getTime());
    }
    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url.replace(" ", "%20").replaceFirst(":443", "");
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Map<String, Object> getExtras(String location) {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }
    public Object getExtra(String key) {
        if (extras == null) {
            return null;
        }
        if (!extras.containsKey(key))
        {
            return null;
        }
        return extras.get(key);
    }
    public Request putExtra(String key, Object value) {
        if (extras == null) {
            extras = new HashMap<String, Object>();
        }
        extras.put(key, value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (priority != request.priority) return false;
        if (!url.equals(request.url)) return false;
        if (!method.equals(request.method)) return false;
        if (!site.equals(request.site)) return false;
        return extras.equals(request.extras);

    }

    @Override
    public int hashCode() {
        int result = (int) (priority ^ (priority >>> 32));
        result = 31 * result + url.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + site.hashCode();
        result = 31 * result + extras.hashCode();
        return result;
    }

    public String getUUID() {
        if (uuid != null) {
            return uuid;
        }
        uuid = UUID.randomUUID().toString();
        return uuid;
    }
    public boolean isComplete()
    {
        if ( site.getRetryTimes() < site.getTryTimes() )
        {
            return true;
        }
        return false;
    }

    public void validatorException()throws BaseException
    {
        if ( isSuccess() ) return;
        throw errorException;
    }
}

