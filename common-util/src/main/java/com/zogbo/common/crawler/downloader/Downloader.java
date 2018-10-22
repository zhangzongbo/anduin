package com.zogbo.common.crawler.downloader;

import com.zogbo.common.crawler.spider.Page;
import com.zogbo.common.crawler.spider.Request;
import com.zogbo.common.crawler.spider.Task;

/**
 * Created by cw on 15-9-11.
 */
public interface Downloader {

    public Page download(Request request, Task task);

    public void setThread(int threadNum);
}
