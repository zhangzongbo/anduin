package com.zobgo.common.crawler.scheduler;


import com.zobgo.common.crawler.spider.Task;

/**
 * Created by cw on 15-9-11.
 */
public interface MonitorableScheduler extends Scheduler{
    public int getLeftRequestsCount(Task task);

    public int getTotalRequestsCount(Task task);
}
