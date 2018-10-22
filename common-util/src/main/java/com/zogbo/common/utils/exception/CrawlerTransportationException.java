package com.zogbo.common.utils.exception;


import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.crawler.enums.CrawlerErrorType;

/**
 * Created by cw on 15-10-13.
 */
public class CrawlerTransportationException extends BaseException {
    public CrawlerTransportationException(String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), message), String.format("[运营商]:%s",messageForClient));
    }

    public CrawlerTransportationException(String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), message), String.format("[运营商]:%s",messageForClient), e);
    }
    public CrawlerTransportationException(ErrorResponse errorResponse)
    {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), errorResponse.message), String.format("[运营商]:%s",errorResponse.messageForClient));
        //super(errorResponse);
    }
    public CrawlerTransportationException(ErrorResponse errorResponse, Throwable e)
    {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), errorResponse.message), String.format("[运营商]:%s",errorResponse.messageForClient), e);
    }
}
