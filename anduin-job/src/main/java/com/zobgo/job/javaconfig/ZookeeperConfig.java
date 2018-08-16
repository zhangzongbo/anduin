package com.zobgo.job.javaconfig;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.val;

/**
 * Created by zongbo.zhang on 8/15/18.

<!-- 配置作业注册中心 -->
<bean id="regCenter" class="com.dangdang.ddframe.reg.zookeeper.ZookeeperRegistryCenter" init-method="init">
<constructor-arg>
<bean class="com.dangdang.ddframe.reg.zookeeper.ZookeeperConfiguration">
<property name="serverLists" value="${xxx}" />
<property name="namespace" value="${xxx}" />
<property name="baseSleepTimeMilliseconds" value="${xxx}" />
<property name="maxSleepTimeMilliseconds" value="${xxx}" />
<property name="maxRetries" value="${xxx}" />
</bean>
</constructor-arg>
</bean>

<!-- 配置作业-->
<bean id="xxxJob" class="com.dangdang.ddframe.job.spring.schedule.SpringJobController" init-method="init">
<constructor-arg ref="regCenter" />
<constructor-arg>
<bean class="com.dangdang.ddframe.job.api.JobConfiguration">
<constructor-arg name="jobName" value="xxxJob" />
<constructor-arg name="jobClass" value="xxxDemoJob" />
<constructor-arg name="shardingTotalCount" value="10" />
<constructor-arg name="cron" value="0/10 * * * * ?" />
<property name="shardingItemParameters" value="${xxx}" />
</bean>
</constructor-arg>
</bean>
*/

@Configuration
public class ZookeeperConfig {
    @Value("${zookeeper.serviceLists}")
    private String serviceLists;

    @Value("${zookeeper.namespace}")
    private String nameSpace;

    @Value("${zookeeper.baseSleepTimeMilliseconds}")
    private int baseSleepTimeMilliseconds;

    @Value("${zookeeper.maxSleepTimeMilliseconds}")
    private int maxSleepTimeMilliseconds;

    @Value("${zookeeper.maxRetries}")
    private int maxRetries;

    /**
     * zookeeper 配置
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(){
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(serviceLists,nameSpace);
        configuration.setBaseSleepTimeMilliseconds(baseSleepTimeMilliseconds);
        configuration.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);
        configuration.setMaxRetries(maxRetries);
        return new ZookeeperRegistryCenter(configuration);
    }
}
