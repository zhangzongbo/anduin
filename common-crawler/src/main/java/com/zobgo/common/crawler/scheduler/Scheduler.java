package com.zobgo.common.crawler.scheduler;


import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Task;

/**
 * Created by cw on 15-9-11.
 */
public interface Scheduler {
    /**
     * add a url to fetch
     *
     * @param request
     * @param task
     */
    public void push(Request request, Task task);

    /**
     * get an url to crawl
     *
     * @param task the task of spider
     * @return the url to crawl
     */
    public Request poll(Task task);
}
