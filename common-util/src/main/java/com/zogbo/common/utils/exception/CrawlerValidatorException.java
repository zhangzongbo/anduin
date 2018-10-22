package com.zogbo.common.utils.exception;

import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.crawler.enums.CrawlerErrorType;

/**
 * Created by cw on 15-9-15.
 */
public class CrawlerValidatorException extends BaseException {
    public CrawlerValidatorException(String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.BUSINESS_ERROR.getValue(), message), messageForClient);
    }

    public CrawlerValidatorException(String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.BUSINESS_ERROR.getValue(), message), messageForClient, e);
    }

}
