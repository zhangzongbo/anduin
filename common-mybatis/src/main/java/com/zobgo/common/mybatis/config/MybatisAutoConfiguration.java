package com.zobgo.common.mybatis.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.github.pagehelper.PageHelper;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/22/18.
 */

@EnableTransactionManagement(proxyTargetClass = true,mode = AdviceMode.ASPECTJ)
@Configuration
@MapperScan("com.zobgo.**.dao")
@Slf4j
public class MybatisAutoConfiguration{
    @Autowired
    private ApplicationContext context;

    @Value("${mybatis.mapperLocations}")
    private String mybatisMapperLocations;

    @Value("${mybatis.configLocation}")
    private String mybatisConfigLocation;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

//    @Value("${spring.datasource.filters}")
//    private String filters;

    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Value("${spring.datasource.auto-commit}")
    private boolean autoCommit = false;

    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setDefaultAutoCommit(autoCommit);
        datasource.setProxyFilters(new ArrayList<Filter>(){{
            add(getLog4jFilter());
            add(getWallFilter());
        }});
//            datasource.setFilters(filters);
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean
    public Filter getWallFilter(){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");

        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);
        wallFilter.setConfig(wallConfig);

        return wallFilter;
    }

    @Bean
    public Filter getLog4jFilter(){
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.setStatementExecutableSqlLogEnable(true);
        return log4jFilter;
    }

    @Bean
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(context.getResource(mybatisConfigLocation));
        sqlSessionFactoryBean.setDataSource(dataSource());
        /**
         *
         * <plugins>
         <!-- com.github.pagehelper为PageHelper类所在包名 -->
         <plugin interceptor="com.github.pagehelper.PageHelper">
         <!-- 4.0.0以后版本可以不设置该参数 -->
         <property name="dialect" value="mysql"/>
         <!-- 该参数默认为false -->
         <!-- 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用 -->
         <!-- 和startPage中的pageNum效果一样-->
         <property name="offsetAsPageNum" value="true"/>
         <!-- 该参数默认为false -->
         <!-- 设置为true时，使用RowBounds分页会进行count查询 -->
         <property name="rowBoundsWithCount" value="true"/>
         <!-- 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果 -->
         <!-- （相当于没有执行分页查询，但是返回结果仍然是Page类型）-->
         <property name="pageSizeZero" value="true"/>
         <!-- 3.3.0版本可用 - 分页参数合理化，默认false禁用 -->
         <!-- 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页 -->
         <!-- 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据 -->
         <property name="reasonable" value="false"/>
         <!-- 3.5.0版本可用 - 为了支持startPage(Object params)方法 -->
         <!-- 增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值 -->
         <!-- 可以配置pageNum,pageSize,count,pageSizeZero,reasonable,orderBy,不配置映射的用默认值 -->
         <!-- 不理解该含义的前提下，不要随便复制该配置 -->
         <property name="params" value="pageNum=pageHelperStart;pageSize=pageHelperRows;"/>
         <!-- 支持通过Mapper接口参数来传递分页参数 -->
         <property name="supportMethodsArguments" value="false"/>
         <!-- always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page -->
         <property name="returnPageInfo" value="none"/>
         </plugin>
         </plugins>
         */

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();

        props.setProperty("dialect", "mysql");
        props.setProperty("offsetAsPageNum", "true");
        props.setProperty("rowBoundsWithCount", "true");
        props.setProperty("pageSizeZero", "false");
        props.setProperty("reasonable", "false");
        props.setProperty("supportMethodsArguments", "false");
        props.setProperty("returnPageInfo", "none");

        pageHelper.setProperties(props);

        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});

        sqlSessionFactoryBean.setMapperLocations(context.getResources(mybatisMapperLocations));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(value = "transactionManager")
    public PlatformTransactionManager getTransactionManager() throws PropertyVetoException, SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
    /**
     * <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator">
     <property name="proxyTargetClass" value="true"/>
     </bean>

     <bean class="org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor">
     <property name="transactionInterceptor" ref="transactionInterceptor"/>
     </bean>

     <bean id="transactionInterceptor" class="org.springframework.transaction.interceptor.TransactionInterceptor">
     <property name="transactionManager" ref="transactionManager"/>
     <property name="transactionAttributeSource">
     <bean class="cz.novoj.business.transactionalRelover.CglibOptimizedAnnotationTransactionAttributeSource"/>
     </property>
     </bean>
     */
    @Bean
    public AnnotationTransactionAspect annotationTransactionAspect() throws PropertyVetoException, SQLException {
        AnnotationTransactionAspect annotationTransactionAspect = AnnotationTransactionAspect.aspectOf();
        annotationTransactionAspect.setTransactionManager(getTransactionManager());
        return annotationTransactionAspect;
    }

    @Bean
    public TransactionAttributeSourceAdvisor transactionAttributeSourceAdvisor() throws PropertyVetoException, SQLException {
        TransactionAttributeSourceAdvisor transactionAttributeSourceAdvisor = new TransactionAttributeSourceAdvisor();
        transactionAttributeSourceAdvisor.setTransactionInterceptor(transactionInterceptor());
        return transactionAttributeSourceAdvisor;
    }

    @Bean
    public TransactionInterceptor transactionInterceptor() throws PropertyVetoException, SQLException {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(getTransactionManager());
        transactionInterceptor.setTransactionAttributeSource(new CglibOptimizedAnnotationTransactionAttributeSource());
        return transactionInterceptor;
    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(getSqlSessionFactory());
    }

    @Bean(name = {"sqlSessionBatch"})
    public SqlSessionTemplate sqlSessionBatch() throws Exception {
        return new SqlSessionTemplate(getSqlSessionFactory(), ExecutorType.BATCH);
    }
}
