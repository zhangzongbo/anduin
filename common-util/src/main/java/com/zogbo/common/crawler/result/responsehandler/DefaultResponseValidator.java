package com.zogbo.common.crawler.result.responsehandler;


import com.zogbo.common.crawler.spider.Request;

/**
 * Created by cw on 15-9-15.
 */
public class DefaultResponseValidator implements  IResponseValidator{
    @Override
    public boolean validate(Request request)throws Exception
    {
        return true;
    }
}
