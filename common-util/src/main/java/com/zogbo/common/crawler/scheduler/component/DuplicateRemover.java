package com.zogbo.common.crawler.scheduler.component;

import com.zogbo.common.crawler.spider.Request;
import com.zogbo.common.crawler.spider.Task;

/**
 * Created by cw on 15-9-11.
 */
public interface DuplicateRemover {
    /**
     *
     * Check whether the request is duplicate.
     *
     * @param request
     * @param task
     * @return
     */
    public boolean isDuplicate(Request request, Task task);

    /**
     * Reset duplicate check.
     * @param task
     */
    public void resetDuplicateCheck(Task task);

    /**
     * Get TotalRequestsCount for monitor.
     * @param task
     * @return
     */
    public int getTotalRequestsCount(Task task);
}
