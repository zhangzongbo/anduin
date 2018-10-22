package com.zogbo.common.crawler.rules;


import com.zogbo.common.crawler.spider.Request;

/**
 * Created by cw on 15-9-19.
 */
public class TelcomMobileRetryRule  implements IRequestRule {
    public Boolean isConformRule(Request request)
    {
        if ( request.isSuccess() ) return false;
        if ( request.isComplete() ) return false;
        if ( request.isBlockException() ) return false;
        return true;
    }
}
