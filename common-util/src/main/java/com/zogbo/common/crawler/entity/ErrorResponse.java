package com.zogbo.common.crawler.entity;

import java.io.Serializable;

/**
 * Created by cw on 15-9-12.
 */
public class ErrorResponse implements Serializable{
    public String code = "E000001";
    public String message;
    public String messageForClient;

    public ErrorResponse()
    {

    }
    public ErrorResponse(String message,String messageForClient)
    {
        this.message = message;
        this.messageForClient = messageForClient;
    }
}
