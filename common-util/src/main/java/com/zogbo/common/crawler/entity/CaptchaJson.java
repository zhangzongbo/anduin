package com.zogbo.common.crawler.entity;

/**
 * Created by 薛云龙 on 2016/11/24.
 */
public class CaptchaJson {
    public CaptchaJson() {
    }
    private String code;
    private String meaasge;
    private String captchaCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMeaasge() {
        return meaasge;
    }

    public void setMeaasge(String meaasge) {
        this.meaasge = meaasge;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
