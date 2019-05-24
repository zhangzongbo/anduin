package com.zobgo.web.demo.exception;



/**
 * Created by cw on 15-11-20.
 */
public class WebException extends BaseException {
    public WebException(IErrorCode iErrorCode, Throwable e) {
        super(iErrorCode, e);
    }

    public WebException(IErrorCode iErrorCode) {
        super(iErrorCode);
    }

    public WebException(String code , String message) {
        super(code,message);
    }
}
