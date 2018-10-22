package com.zogbo.common.crawler.spider;

/**
 * Created by cw on 15-9-11.
 */
public interface SpiderListener {
    void onSuccess(Request request);

    void onError(Request request);
}
