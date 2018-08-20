package com.zobgo.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/17/18.
 *
 * 任务总监听器
 */

@Slf4j
public class MyElasticJobListener implements ElasticJobListener{

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("===> JOB BEGIN: {} <===",date2Str(new Date()));
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("===> JOB END: {} <===",date2Str(new Date()));
    }

    private String date2Str(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        MyElasticJobListener listener = new MyElasticJobListener();
        System.out.println(listener.date2Str(new Date()));
    }
}
