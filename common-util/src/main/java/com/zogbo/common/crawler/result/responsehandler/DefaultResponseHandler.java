package com.zogbo.common.crawler.result.responsehandler;


import com.zogbo.common.crawler.spider.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cw on 15-9-11.
 */
public class DefaultResponseHandler extends AbstractResponseHandler {

    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(DefaultResponseHandler.class);
    @Override
    protected void setResult(Request request)throws Exception
    {
        request.getPage().setResult(request.getCookie()+formatCookies(request.getPage().getCookies())+ "$" +request.getPage().getHtml());
    }
}
