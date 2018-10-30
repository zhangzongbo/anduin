package com.zobgo.common.crawler.scheduler.component;

import com.google.common.collect.Sets;


import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cw on 15-9-11.
 */
public class HashSetDuplicateRemover implements DuplicateRemover {
    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(HashSetDuplicateRemover.class);
    private Set<String> requests = Sets.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    //TODO
    @Override
    public boolean isDuplicate(Request request, Task task)
    {
        return false;
        //return !requests.add(request.getUUID());
    }
    @Override
    public void resetDuplicateCheck(Task task) {
        requests.clear();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return requests.size();
    }
}
