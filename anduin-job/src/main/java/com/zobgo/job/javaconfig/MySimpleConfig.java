package com.zobgo.job.javaconfig;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zobgo.job.simple.MySimpleJob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by zongbo.zhang on 8/16/18.
 */

@Configuration
public class MySimpleConfig {

    @Resource
    private ZookeeperRegistryCenter registryCenter;

    @Value("${simpleJob.mySimpleJob.name}")
    private String mySimpleJobName;

    @Value("${simpleJob.mySimpleJob.cron}")
    private String mySimpleJobCron;

    @Value("${simpleJob.mySimpleJob.shardingTotalCount}")
    private int mySimpleJobShardingTotalCount;

    @Bean
    public MySimpleJob mySimpleJob(){
        return new MySimpleJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler mySimpleJobScheduler(final MySimpleJob mySimpleJob){
        return new SpringJobScheduler(mySimpleJob,registryCenter, liteJobConfiguration(),new ElasticJobListener[0]);
    }

    public LiteJobConfiguration liteJobConfiguration(){
        //定义Lite作业根配置
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(mySimpleJobName,mySimpleJobCron,mySimpleJobShardingTotalCount).build(),
                MySimpleJob.class.getCanonicalName()
        )).overwrite(true).build();
    }
}
