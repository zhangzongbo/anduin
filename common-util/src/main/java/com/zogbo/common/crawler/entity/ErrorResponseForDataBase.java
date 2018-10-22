package com.zogbo.common.crawler.entity;

import java.io.Serializable;

/**
 * Created by cw on 15-9-12.
 */
public class ErrorResponseForDataBase implements Serializable{
    public String code = "E000001";
    public String message;
    public ErrorResponseForDataBase(ErrorResponse errorResponse)
    {
        this.code = errorResponse.code;
        this.message = errorResponse.messageForClient;
    }
    public ErrorResponseForDataBase(String message)
    {
        this.message = message;
    }
    public ErrorResponseForDataBase(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
