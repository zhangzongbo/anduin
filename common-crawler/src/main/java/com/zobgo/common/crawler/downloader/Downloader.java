package com.zobgo.common.crawler.downloader;


import com.zobgo.common.crawler.spider.Page;
import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Task;

/**
 * Created by cw on 15-9-11.
 */
public interface Downloader {

    public Page download(Request request, Task task);

    public void setThread(int threadNum);
}
