package com.zogbo.common.crawler.entity;

import java.io.Serializable;

/**
 * Created by huangpin on 16/2/17.
 */
public class Result<T> implements Serializable{
    /**
     * 状态码
     */
    private String code;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
