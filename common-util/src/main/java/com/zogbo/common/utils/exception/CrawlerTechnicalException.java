package com.zogbo.common.utils.exception;
import com.zogbo.common.crawler.entity.ErrorResponse;
import com.zogbo.common.crawler.enums.CrawlerErrorType;

/**
 * 服务器端错误。
 * 系统遇到此类错误后不应该重试。
 * 监控系统遇到此类错误时应立即报警。
 * 业务员遇到此类错误时应启动应急方案。
 */
public class CrawlerTechnicalException extends BaseException {

    public CrawlerTechnicalException(String message, String messageForClient) {
        super(String.format("%s: %s", CrawlerErrorType.COMPONENT_ERROR.getValue(), message), messageForClient);
    }

    public CrawlerTechnicalException(String message, String messageForClient, Throwable e) {
        super(String.format("%s: %s", CrawlerErrorType.COMPONENT_ERROR.getValue(), message), messageForClient, e);
    }
}
