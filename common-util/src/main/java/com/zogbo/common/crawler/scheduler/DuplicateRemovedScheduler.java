package com.zogbo.common.crawler.scheduler;



import com.zogbo.common.crawler.scheduler.component.DuplicateRemover;
import com.zogbo.common.crawler.scheduler.component.HashSetDuplicateRemover;
import com.zogbo.common.crawler.spider.Request;
import com.zogbo.common.crawler.spider.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cw on 15-9-11.
 */
public abstract class DuplicateRemovedScheduler implements Scheduler{
    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(DuplicateRemovedScheduler.class);
    private DuplicateRemover duplicatedRemover = new HashSetDuplicateRemover();

    public DuplicateRemover getDuplicateRemover() {
        return duplicatedRemover;
    }

    public DuplicateRemovedScheduler setDuplicateRemover(DuplicateRemover duplicatedRemover) {
        this.duplicatedRemover = duplicatedRemover;
        return this;
    }

    @Override
    public void push(Request request, Task task) {
        logger.trace("get a candidate url {}", request.getUrl());
        if (!duplicatedRemover.isDuplicate(request, task) || shouldReserved(request)) {
            logger.debug("push to queue {}", request.getUrl());
            pushWhenNoDuplicate(request, task);
        }
    }

    protected boolean shouldReserved(Request request) {
        return request.getExtra(Request.CYCLE_TRIED_TIMES) != null;
    }

    protected void pushWhenNoDuplicate(Request request, Task task) {

    }
}
