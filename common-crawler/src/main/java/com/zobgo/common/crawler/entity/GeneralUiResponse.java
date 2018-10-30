package com.zobgo.common.crawler.entity;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Dto for general ui response
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2016-02-01 09:04
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GeneralUiResponse {
    private Integer fields;
    private Integer timeout = 30;
    private String captcha;
    private String reqCaptcha;
    private String captchaExt;
    private String reqCaptchaExt;
    private String smsTooltips;
    private String sms;
    private String smsDes;
    private String reqSmsDes;
    private String reqSms;
    private String nextStep;
    private String PasswordDes;
    private String findPassword;
    private NextStepOption nextStepOption;

    @JsonProperty("sms_tooltips")
    public String getSmsTooltips() {
        return smsTooltips;
    }

    public void setSmsTooltips(String smsTooltips) {
        this.smsTooltips = smsTooltips;
    }

    @JsonProperty("req_sms_des")
    public String getReqSmsDes() {
        return reqSmsDes;
    }

    public void setReqSmsDes(String reqSmsDes) {
        this.reqSmsDes = reqSmsDes;
    }

    @JsonProperty("find_password")
    public String getFindPassword() {
		return findPassword;
	}

	public void setFindPassword(String findPassword) {
		this.findPassword = findPassword;
	}

	@JsonProperty("fields")
    public Integer getFields() {
        return fields;
    }

    public void setFields(Integer fields) {
        this.fields = fields;
    }

    @JsonProperty("timeout")
    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @JsonProperty("captcha")
    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @JsonProperty("req_captcha")
    public String getReqCaptcha() {
        return reqCaptcha;
    }

    public void setReqCaptcha(String req_captcha) {
        this.reqCaptcha = req_captcha;
    }

    @JsonProperty("captcha_ext")
    public String getCaptchaExt() {
        return captchaExt;
    }

    public void setCaptcha_ext(String captcha_ext) {
        this.captchaExt = captcha_ext;
    }

    @JsonProperty("req_captcha_ext")
    public String getReqCaptchaExt() {
        return reqCaptchaExt;
    }

    public void setReqCaptchaExt(String req_captcha_ext) {
        this.reqCaptchaExt = req_captcha_ext;
    }

    @JsonProperty("sms")
    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    @JsonProperty("sms_des")
    public String getSmsDes() {
        return smsDes;
    }

    public void setSmsDes(String sms_des) {
        this.smsDes = sms_des;
    }

    @JsonProperty("req_sms")
    public String getReqSms() {
        return reqSms;
    }

    public void setReqSms(String req_sms) {
        this.reqSms = req_sms;
    }

    @JsonProperty("next_step")
    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    @JsonProperty("password_des")
    public String getPasswordDes() {
        return PasswordDes;
    }

    public void setPasswordDes(String password_des) {
        this.PasswordDes = password_des;
    }

    public void setCaptchaExt(String captchaExt) {
        this.captchaExt = captchaExt;
    }

    @JsonProperty("next_step_option")
    public NextStepOption getNextStepOption() {
        return nextStepOption;
    }

    public void setNextStepOption(NextStepOption nextStepOption) {
        this.nextStepOption = nextStepOption;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public class NextStepOption {
        private boolean show;
        private boolean refresh;

        @JsonProperty("show")
        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        @JsonProperty("refresh")
        public boolean isRefresh() {
            return refresh;
        }

        public void setRefresh(boolean refresh) {
            this.refresh = refresh;
        }
    }
}
