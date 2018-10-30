package com.zobgo.common.crawler.rules;


import com.zobgo.common.crawler.spider.Request;

/**
 * Created by cw on 15-9-23.
 */
public class XiAnTelcomMobileRetryRule  implements IRequestRule {
    public Boolean isConformRule(Request request)
    {
        if ( request.isSuccess() ) return false;
        if ( request.isComplete() ) return false;
        if ( request.isBlockException() ) return false;
        return true;
    }
}
