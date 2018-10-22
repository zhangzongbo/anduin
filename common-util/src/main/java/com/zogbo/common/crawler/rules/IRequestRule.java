package com.zogbo.common.crawler.rules;


import com.zogbo.common.crawler.spider.Request;

/**
 * Created by cw on 15-9-15.
 */
public interface IRequestRule extends IRule<Request,Boolean>{
    Boolean isConformRule(Request request);
}
