package com.zobgo.common.crawler.rules;


import com.zobgo.common.crawler.exception.CrawlerNetworkException;
import com.zobgo.common.crawler.exception.CrawlerReversionException;
import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.utils.HttpProxyUtil;

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
