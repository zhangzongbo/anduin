package com.zogbo.common.utils.exception;

import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.crawler.enums.CrawlerErrorType;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-08-19 15:23
 */
public class CrawlerNetworkException extends BaseException {
    public CrawlerNetworkException(String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.NETWORK_ERROR.getValue(), message), messageForClient);
    }

    public CrawlerNetworkException(String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.NETWORK_ERROR.getValue(), message), messageForClient, e);
    }
    public CrawlerNetworkException(ErrorResponse errorResponse)
    {
        super(errorResponse);
    }

    public CrawlerNetworkException(ErrorResponse errorResponse, Throwable e)
    {
        super(errorResponse,e);
    }
}
