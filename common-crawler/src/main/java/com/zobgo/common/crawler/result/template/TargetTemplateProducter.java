package com.zobgo.common.crawler.result.template;

import com.google.gson.Gson;


import com.zobgo.common.crawler.exception.CrawlerTechnicalException;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.InputStream;

import us.codecraft.webmagic.selector.Html;

/**
 * Created by cw on 15-9-14.
 */
public abstract class TargetTemplateProducter extends AbstractTemplate{
    public abstract AbstractTemplate createTargetTemplate()throws Exception;

    /**
     * 是否需要反序列化,当返回值为html时,不能被反序列化
     * @return
     */
    public abstract boolean needDeSerializable();

    /**
     * 返回的是String
     * @return
     */
    public boolean isStringContent(){
        return true;
    }

    /**
     *原始返回报文
     */
    protected String content;

    /*
    * 原始返回HTML
    * */
    protected transient Html html;

    public void setHtml(Html html) {
        this.html = html;
    }

    /**
     * 原始返回报文流
     */
    protected InputStream inputStream;

    @JsonIgnore
    public TargetTemplateProducter setAsMapper(String json,TargetTemplateProducter type) {
        type.content = json;
        if ( !needDeSerializable() )
        {//如果不需要反序列化
            return type;
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, type.getClass());
        } catch (Exception e) {
            throw new CrawlerTechnicalException("json反序列化到Java对象发生异常", "系统暂时不可用，请稍后重试", e);
        }
    }

    public TargetTemplateProducter setAsMapper(InputStream inputStream,TargetTemplateProducter type) {
        type.inputStream = inputStream;
        return type;
    }

}