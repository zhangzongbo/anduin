package com.zobgo.common.crawler.downloader;


import com.zobgo.common.crawler.spider.Request;

/**
 * Created by cw on 15-9-11.
 */
public abstract class AbstractDownloader implements Downloader{
    protected void onSuccess(Request request) {
    }

    protected void onError(Request request) {
    }
}
