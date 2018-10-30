package com.zobgo.common.crawler.exception;


import com.zobgo.common.crawler.enums.CrawlerErrorType;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-08-19 15:26
 */
public class CrawlerDependencyException extends BaseException {
    public CrawlerDependencyException(String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.FATAL_ERROR.getValue(), message), messageForClient);
    }

    public CrawlerDependencyException(String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.FATAL_ERROR.getValue(), message), messageForClient, e);
    }
}
