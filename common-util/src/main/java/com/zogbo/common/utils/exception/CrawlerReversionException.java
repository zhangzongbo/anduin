package com.zogbo.common.utils.exception;


import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.crawler.enums.CrawlerErrorType;

/**
 * Created by cw on 15-10-13.
 */
public class CrawlerReversionException extends BaseException {
    private String key;
    public CrawlerReversionException(String key, String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), message), String.format("[错误]:%s",messageForClient));
        this.key = key;
    }

    public CrawlerReversionException(String key, String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), message), String.format("[错误]:%s",messageForClient), e);
        this.key = key;
    }
    public CrawlerReversionException(String key, ErrorResponse errorResponse)
    {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), errorResponse.message), String.format("[错误]:%s",errorResponse.messageForClient));
        this.key = key;
    }
    public CrawlerReversionException(String key, ErrorResponse errorResponse, Throwable e)
    {
        super(String.format("%s: %s", CrawlerErrorType.TRANSPORTATION_ERROR.getValue(), errorResponse.message), String.format("[错误]:%s",errorResponse.messageForClient), e);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
