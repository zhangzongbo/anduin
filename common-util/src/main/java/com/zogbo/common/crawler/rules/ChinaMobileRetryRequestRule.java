package com.zogbo.common.crawler.rules;


import com.zogbo.common.crawler.spider.Request;
import com.zogbo.common.utils.HttpProxyUtil;
import com.zogbo.common.utils.exception.CrawlerNetworkException;
import com.zogbo.common.utils.exception.CrawlerReversionException;

/**
 * Created by cw on 15-9-15.
 */
public class ChinaMobileRetryRequestRule implements IRequestRule {
    public Boolean isConformRule(Request request)
    {
        if ( request.isSuccess() ) return false;
        if ( request.isComplete() ) return false;
        if ( (int)request.getExtra(Request.STATUS_CODE) >= 500){
            request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(request.getKey()));
            return true;
        }
        if ( request.getErrorException() instanceof CrawlerNetworkException){
            request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(request.getKey()));
            return true;
        }
        if (request.getErrorException() instanceof CrawlerReversionException){
            request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(request.getKey()));
            return true;
        }
        if ( request.isBlockException() ) return false;
        request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(request.getKey()));
        return true;
    }
}
