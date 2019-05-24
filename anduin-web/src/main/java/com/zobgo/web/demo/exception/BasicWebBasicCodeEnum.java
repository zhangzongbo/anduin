package com.zobgo.web.demo.exception;


/**
 * Created by chenwen on 16/12/19.
 */
public enum BasicWebBasicCodeEnum implements IErrorCode {
    ILLEGAL_ARGUMENT_ERROR("请求参数不合法,请核对参数"),
    ARGUMENT_PARSE_ERROR("参数解析异常"),
    NOT_SUPPORTED_METHOD_ERROR("不支持的请求方式"),
    PERMISSION_ERROR("权限不足"),
    USERNAME_OR_PASSWORD_ERROR("用户名或密码错误")
    ;

    private String message;

    BasicWebBasicCodeEnum(String message){
        this.message = message;
    }

    @Override
    public String getCode() {
        int startCode = 2;
        return getCode(startCode,ordinal());
    }

    @Override
    public String getMessage() {
        return message;
    }
}
