package com.zogbo.common.utils.exception;


import com.zogbo.common.utils.enums.IErrorCode;

/**
 * Created by cw on 15-11-20.
 */
public class BusinessException extends BaseException {
    public BusinessException(IErrorCode iErrorCode, Throwable e) {
        super(iErrorCode, e);
    }

    public BusinessException(IErrorCode iErrorCode) {
        super(iErrorCode);
    }

    public BusinessException(String code , String message) {
        super(code,message);
    }
}
