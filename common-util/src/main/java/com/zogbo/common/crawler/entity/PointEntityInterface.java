package com.zogbo.common.crawler.entity;

import java.util.Map;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-12-10 21:46
 */
public interface PointEntityInterface {
    Map<String, String> getTags();
    Map<String, Object> getValues();
}
