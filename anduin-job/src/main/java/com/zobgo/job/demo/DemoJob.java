package com.zobgo.job.demo;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/23/18.
 */

@Slf4j
@Component
public class DemoJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("<====Demo Job Begin====>");
        log.info(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
                Thread.currentThread().getId(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("<====Demo Job End====>");
    }
}
