package com.zobgo.common.crawler.rules;


import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.utils.HttpProxyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zhangzb on 17-7-24.
 */
@Slf4j
public class YunNanChinaMobileRetryRule implements IRequestRule {

    @Override
    public Boolean isConformRule(Request request) {
        if (request.getPage().getOriginHtml().contains("超出查询范围"))
        {
            log.info("retry######$#33333333");
            request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(request.getKey()));
            return true;
        }
        if ( request.isSuccess() ) return false;
        if ( request.isComplete() ) return false;
        if ( request.isBlockException() ) return false;
        request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(request.getKey()));
        return true;
    }
}
