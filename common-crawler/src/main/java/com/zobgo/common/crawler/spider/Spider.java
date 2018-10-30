package com.zobgo.common.crawler.spider;


import com.zobgo.common.crawler.downloader.Downloader;
import com.zobgo.common.crawler.downloader.HttpClientDownloader;
import com.zobgo.common.crawler.entity.ErrorResponse;
import com.zobgo.common.crawler.scheduler.QueueScheduler;
import com.zobgo.common.crawler.scheduler.Scheduler;
import com.zogbo.common.utils.UrlUtils;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by cw on 15-9-11.
 */
public class Spider implements Task,Runnable {
    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(Spider.class);

    protected List<SpiderListener> spiderListeners;
    protected List<Request> startRequests;
    protected Scheduler scheduler = new QueueScheduler();
    protected Downloader downloader;
    protected CountableThreadPool threadPool;
    protected ExecutorService executorService;
    protected String uuid;
    protected int threadNum = 1;
    protected AtomicInteger stat = new AtomicInteger(STAT_INIT);
    protected boolean exitWhenComplete = true;
    protected int waitTimeWhenOneThread = 1000;
    protected final static int STAT_INIT = 0;

    protected final static int STAT_RUNNING = 1;

    protected final static int STAT_STOPPED = 2;

    protected boolean spawnUrl = true;

    protected boolean destroyWhenExit = true;

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private final AtomicLong pageCount = new AtomicLong(0);

    private Date startTime;

    private int emptySleepTime = 8000;
    //private static Spider UnicomMobileInstance = new Spider().thread(100).setExitWhenComplete(false);
    private Spider()
    {

    }

//    public static Spider getInstance(ServiceProviderTypeEnum type) {
//        switch (type) {
//            case CHINA_MOBILE:
//                break;
//            case CHINA_UNICOM:
//                if (UnicomMobileInstance.stat.get() != STAT_RUNNING)
//                {
//                    UnicomMobileInstance.runAsync();
//                }
//                return UnicomMobileInstance;
//            case CHINA_TELECOM:
//                break;
//            default:
//                throw new CrawlerBusinessException("运营商类别错误", "运营商类别错误");
//        }
//        return new Spider();
//    }

    public static Spider create()
    {
        return new Spider();
    }
    @Override
    public void run()
    {

        checkRunningStat();
        initComponent();
        logger.info("Spider " + getUUID() + " started!");

        //TODO
//        while (stat.get() == STAT_RUNNING) {
        while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
            final Request request = scheduler.poll(this);

            if (request == null) {
                logger.info(String.format("no request threadAlive = %s exitWhenComplete = %s thread.isInterrupted = %s",threadPool.getThreadAlive(),exitWhenComplete,Thread.currentThread().isInterrupted()));

                if (threadPool.getThreadAlive() == 0 && exitWhenComplete)
                {
                    break;
                }
                if ( threadPool.getThreadAlive() > 0 && exitWhenComplete )
                {
                    long startTime = System.currentTimeMillis();
                    while (true)
                    {
                        long endTime = System.currentTimeMillis();
                        if (threadPool.getThreadAlive() == 0 && exitWhenComplete)
                        {
                            break;
                        }
                        if ( endTime - startTime > 2*1000)
                        {
                            break;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            logger.error("InterruptedException for sleep",e);
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
                if (Thread.currentThread().isInterrupted()){
                    break;
                }
                // wait until new url added
                waitNewUrl();
            } else {
                final Request requestFinal = request;
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("start request mobile=" + request.getKey() + " url=" + request.getUrl());
                        ;
                        ErrorResponse errorResponse = new ErrorResponse();
                        try {
                            processRequest(requestFinal);
                            if (requestFinal.isSuccess()) {
                                onSuccess(requestFinal);
                            } else {
                                onError(requestFinal);
                            }
                        } catch (Exception e) {
                            onError(requestFinal);
                            logger.error("process request " + requestFinal + " error", e);
                            errorResponse.message = String.format("mobile: %s,获取中国联通验证码失败", requestFinal.getKey(
                            ));
                            errorResponse.messageForClient = "服务异常";
                            requestFinal.setErrorResponse(errorResponse);
                        } finally {
                            //TODO PROXY
//                            if (requestFinal.getSite().getHttpProxyPool()!=null && requestFinal.getSite().getHttpProxyPool().isEnable()) {
//                                requestFinal.getSite().returnHttpProxyToPool((HttpHost) requestFinal.getExtra(Request.PROXY), (Integer) requestFinal
//                                        .getExtra(Request.STATUS_CODE));
//                            }
                            logger.info("end request mobile=" + request.getKey() + " url=" + request.getUrl() + " method=" + request.getMethod());
                            requestFinal.getSite().incrementAndGetTryTimes();
                            pageCount.incrementAndGet();
                            //如果没有成功,并且请求次数没到上限,则重试
                            if (requestFinal.isNeedRetry()) {
                                logger.info("!requestFinal.isSuccess() && !requestFinal.isComplete()");
                                addRequest(requestFinal);
                            }
                            signalNewUrl();
                        }
                    }
                });
            }
        }


        stat.set(STAT_STOPPED);
        // release some resources
        if (destroyWhenExit) {
            close();
        }

    }

    public boolean isDestroyWhenExit() {
        return destroyWhenExit;
    }

    public Spider setDestroyWhenExit(boolean destroyWhenExit) {
        this.destroyWhenExit = destroyWhenExit;
        return this;
    }

    protected void onError(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onError(request);
            }
        }
    }

    protected void onSuccess(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onSuccess(request);
            }
        }
    }

    public void waitCompleteForRequests(Request... requests)
    {
        while (true) {
            boolean isComplete = true;
            for (Request request : requests) {
                if (!request.isComplete())
                {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete)
            {
                break;
            }
            sleep(100);

        }
    }

    /**
     * Set startUrls of Spider.<br>
     * Prior to startUrls of Site.
     *
     * @param startRequests
     * @return this
     */
    public Spider startRequest(List<Request> startRequests) {
        checkIfRunning();
        this.startRequests = startRequests;
        return this;
    }
    /**
     * set scheduler for Spider
     *
     * @param scheduler
     * @return this
     * @Deprecated
     * @see #
     */
    public Spider scheduler(Scheduler scheduler) {
        return setScheduler(scheduler);
    }

    /**
     * set scheduler for Spider
     *
     * @param scheduler
     * @return this
     * @see Scheduler
     * @since 0.2.1
     */
    public Spider setScheduler(Scheduler scheduler) {
        checkIfRunning();
        Scheduler oldScheduler = this.scheduler;
        this.scheduler = scheduler;
        if (oldScheduler != null) {
            Request request;
            while ((request = oldScheduler.poll(this)) != null) {
                this.scheduler.push(request, this);
            }
        }
        return this;
    }

    /**
     * set the downloader of spider
     *
     * @param downloader
     * @return this
     * @see #
     * @deprecated
     */
    public Spider downloader(Downloader downloader) {
        return setDownloader(downloader);
    }

    /**
     * set the downloader of spider
     *
     * @param downloader
     * @return this
     * @see Downloader
     */
    public Spider setDownloader(Downloader downloader) {
        checkIfRunning();
        this.downloader = downloader;
        return this;
    }

    protected void initComponent() {
        logger.info("start initComponent");
        if (downloader == null) {
            this.downloader = new HttpClientDownloader();
        }
        if (threadNum <= 1)
        {
            waitTimeWhenOneThread = emptySleepTime;
        }
        downloader.setThread(threadNum);
        if (threadPool == null || threadPool.isShutdown()) {
            if (executorService != null && !executorService.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum, executorService);
            } else {
                threadPool = new CountableThreadPool(threadNum);
            }
        }
        if (startRequests != null) {
            for (Request request : startRequests) {
                scheduler.push(request, this);
            }
            startRequests.clear();
        }
        startTime = new Date();
        logger.info("end initComponent");
    }

    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("Spider is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }

    public void close() {
        destroyEach(downloader);
        if (threadPool != null) threadPool.shutdown();
    }

    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void processRequest(Request request) {
        Page page = downloader.download(request, this);
    }
    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addRequest(Request request) {
        if (request.getSite().getDomain() == null && request != null && request.getUrl() != null) {
            request.getSite().setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        scheduler.push(request, this);
    }

    protected void checkIfRunning() {
        if (stat.get() == STAT_RUNNING) {
            throw new IllegalStateException("Spider is already running!");
        }
    }

    public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }


    /**
     * Add urls with information to crawl.<br/>
     *
     * @param requests
     * @return
     */
    public Spider addRequest(Request... requests) {
        for (Request request : requests) {
            addRequest(request);
        }
        signalNewUrl();
        return this;
    }

    private void waitNewUrl()
    {
        newUrlLock.lock();
        try {
            //logger.info("threadPool.getThreadAlive():"+threadPool.getThreadAlive());
            //double check
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            if (threadPool.getThreadAlive() <= 1 && exitWhenComplete )
            {
                emptySleepTime = waitTimeWhenOneThread;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("waitNewUrl - interrupted, error {}", e);
            Thread.currentThread().interrupt();
        } finally {
            newUrlLock.unlock();
        }
    }

    private void signalNewUrl() {
        try {
            newUrlLock.lock();
            logger.info("signalNewUrl");
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

    public int getWaitTimeWhenOneThread() {
        return waitTimeWhenOneThread;
    }

    public Spider setWaitTimeWhenOneThread(int waitTimeWhenOneThread) {
        this.waitTimeWhenOneThread = waitTimeWhenOneThread;
        return this;
    }

    public void start() {
        runAsync();
    }

    public void stop() {
        if (stat.compareAndSet(STAT_RUNNING, STAT_STOPPED)) {
            logger.info("Spider " + getUUID() + " stop success!");
        } else {
            logger.info("Spider " + getUUID() + " stop fail!");
        }
        //signalNewUrl();
    }

    /**
     * start with more than one threads
     *
     * @param threadNum
     * @return this
     */
    public Spider thread(int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        return this;
    }

    /**
     * start with more than one threads
     *
     * @param threadNum
     * @return this
     */
    public Spider thread(ExecutorService executorService, int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        return this;
    }

    public boolean isExitWhenComplete() {
        return exitWhenComplete;
    }

    /**
     * Exit when complete. <br/>
     * True: exit when all url of the site is downloaded. <br/>
     * False: not exit until call stop() manually.<br/>
     *
     * @param exitWhenComplete
     * @return
     */
    public Spider setExitWhenComplete(boolean exitWhenComplete) {
        this.exitWhenComplete = exitWhenComplete;
        return this;
    }

    public boolean isSpawnUrl() {
        return spawnUrl;
    }

    /**
     * Get page count downloaded by spider.
     *
     * @return total downloaded page count
     * @since 0.4.1
     */
    public long getPageCount() {
        return pageCount.get();
    }

    /**
     * Get running status by spider.
     *
     * @return running status
     * @see Status
     * @since 0.4.1
     */
    public Status getStatus() {
        return Status.fromValue(stat.get());
    }

    public enum Status {
        Init(0), Running(1), Stopped(2);

        private Status(int value) {
            this.value = value;
        }

        private int value;

        int getValue() {
            return value;
        }

        public static Status fromValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            //default value
            return Init;
        }
    }

    /**
     * Get thread count which is running
     *
     * @return thread count which is running
     * @since 0.4.1
     */
    public int getThreadAlive() {
        if (threadPool == null) {
            return 0;
        }
        return threadPool.getThreadAlive();
    }

    /**
     * Whether add urls extracted to download.<br>
     * Add urls to download when it is true, and just download seed urls when it is false. <br>
     * DO NOT set it unless you know what it means!
     *
     * @param spawnUrl
     * @return
     * @since 0.4.0
     */
    public Spider setSpawnUrl(boolean spawnUrl) {
        this.spawnUrl = spawnUrl;
        return this;
    }

    @Override
    public String getUUID() {
        if (uuid != null) {
            return uuid;
        }
        uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public Spider setExecutorService(ExecutorService executorService) {
        checkIfRunning();
        this.executorService = executorService;
        return this;
    }
    public Date getStartTime() {
        return startTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Set wait time when no url is polled.<br></br>
     *
     * @param emptySleepTime In MILLISECONDS.
     */
    public void setEmptySleepTime(int emptySleepTime) {
        this.emptySleepTime = emptySleepTime;
    }

}
