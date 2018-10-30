package com.zobgo.common.crawler.result.responsehandler;


import com.zobgo.common.crawler.spider.Request;

/**
 * Created by cw on 15-9-15.
 */
public interface IResponseValidator {
    boolean validate(Request request)throws Exception;
}
