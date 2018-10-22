package com.zogbo.common.crawler.scheduler;


import com.zogbo.common.crawler.spider.Task;

/**
 * Created by cw on 15-9-11.
 */
public interface MonitorableScheduler extends Scheduler{
    public int getLeftRequestsCount(Task task);

    public int getTotalRequestsCount(Task task);
}
