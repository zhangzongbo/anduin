package com.zogbo.common.crawler.result.template;


import com.zogbo.common.utils.exception.CrawlerTechnicalException;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by cw on 15-9-14.
 */
public abstract class AbstractTemplate {
    @JsonIgnore
    public String getAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        String result;
        try {
            result = mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new CrawlerTechnicalException("Java对象序列化到json发生异常", "系统暂时不可用，请稍后重试", e);
        }

        return result;
    }
}
