package com.zobgo.common.crawler.scheduler;



import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by cw on 15-9-11.
 */
public class QueueScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler {
    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(QueueScheduler.class);
    private ConcurrentLinkedQueue<Request> queue = new ConcurrentLinkedQueue<Request>();

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        queue.add(request);
    }

    @Override
    public synchronized Request poll(Task task) {
        return queue.poll();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return queue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getTotalRequestsCount(task);
    }

}