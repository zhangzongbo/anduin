package com.zogbo.common.utils.exception;
import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.utils.enums.IErrorCode;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-08-19 15:18
 */
public class BaseException extends RuntimeException {
    private String code;

    private IErrorCode iErrorCode;

//    public BaseException(String code, Throwable e) {
//        super(Config.getString(code), e);
//        this.code = code;
//    }
//
//    public BaseException(String code) {
//        super(Config.getString(code));
//        this.code = code;
//    }
//
//    public BaseException(String code, String message) {
//        super(message);
//        this.code = code;
//    }

    public BaseException(ErrorResponse errorResponse)
    {
        super(errorResponse.message);
    }

    public BaseException(ErrorResponse errorResponse,Throwable e)
    {
        super(errorResponse.message,e);
    }

    public BaseException(IErrorCode iErrorCode, Throwable e) {
        super(iErrorCode.getMessage(), e);
        this.code = iErrorCode.getCode();
        this.iErrorCode = iErrorCode;
    }

    public BaseException(IErrorCode iErrorCode) {
        super(iErrorCode.getMessage());
        this.code = iErrorCode.getCode();
        this.iErrorCode = iErrorCode;
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String code, String message,Throwable e) {
        super(message,e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public IErrorCode getiErrorCode() {
        return iErrorCode;
    }
}
