package com.zogbo.common.utils.exception;

import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.crawler.enums.CrawlerErrorType;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-08-20 15:44
 */
public class CrawlerBusinessException extends BaseException {
    public CrawlerBusinessException(String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.BUSINESS_ERROR.getValue(), message), messageForClient);
    }

    public CrawlerBusinessException(String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.BUSINESS_ERROR.getValue(), message), messageForClient, e);
    }
    public CrawlerBusinessException(ErrorResponse errorResponse)
    {
        super(errorResponse);
    }

    public CrawlerBusinessException(ErrorResponse errorResponse, Throwable e)
    {
        super(errorResponse,e);
    }
}
