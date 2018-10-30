package com.zobgo.common.crawler.enums;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-08-19 15:02
 */
public enum CrawlerErrorType implements StringValueEnum {
    /**
     * 爬虫上游服务异常
     * 发现上游服务异常，比如发现上游服务返回不可用；
     */
    FATAL_ERROR("[FATAL ERROR]"),

    /**
     * 爬虫网络异常
     * 请求上游服务时，发生网络问题，比如重试三次后依然不可用，SSL问题，路由、域名、DNS、返回400以上的status code等
     */
    NETWORK_ERROR("[NETWORK ERROR]"),

    /**
     * 爬虫数据提取异常
     * 解析返回页面时找不到预定的元素
     */
    DATA_PARSE_ERROR("[DATA PARSE ERROR]"),

    /**
     * 爬虫业务异常
     * 爬虫处理中任何业务处理，且影响流程继续进行的问题，如没有登录就去申请发短信等没有按照流程走所发生的异常
     */
    BUSINESS_ERROR("[BUSINESS ERROR]"),

    /**
     * 爬虫中间过程组件异常
     * 爬虫业务处理过程中任务技术问题所导致的异常，这类异常会影响流程的进行，如格式转换错误，算法不支持，空指针等
     */
    COMPONENT_ERROR("[COMPONENT ERROR]"),

    /**
     * 不外抛出，仅仅记录的异常
     * 爬取过程中，发现某些数据需要记录，但是不影响下一步流程的进行，仅仅只需要记录而已
     */
    ONLY_LOG_ERROR("[ONLY LOG]"),

    /**
     * 爬虫保存数据异常-mongodb
     * 数据层mongodb操作异常
     */
    DATABASE_MONGODB_ERROR("[MONGODB ERROR]"),

    /**
     * 爬虫保存数据异常-dbms
     * 数据出dbms操作异常
     */
    DATABASE_DBMS_ERROR("[DBMS ERROR]"),

    /**
     * 爬虫未知错误
     * 未知错误，所有影响流程进行，但是没有预先定义的错误
     */
    UNKNOWN_ERROR("[UNKNOWN ERROR]"),

    /**
     * 不支持的业务
     * 需要转发给原始服务器
     */
    BUSINESS_REDIRECT("[BUSINESS REDIRECT]"),


    /**
     * 运营商异常
     */
    TRANSPORTATION_ERROR("[TRANSPORTATION ERROR]"),


    /**
     * 打码错误
     */
    AUTO_CAPTCHA_ERROR("[AUTO CAPTCHA ERROR]"),


    /**
     * 刷新模板,但是不进行下一步
     */
    REFRESH_TEMPLATE_ERROR("[REFRESH TEMPLATE ERROR]");

    private final String strValue;

    CrawlerErrorType(String strValue) {
        this.strValue = strValue;
    }

    @Override
    public String getValue() {
        return strValue;
    }
}
