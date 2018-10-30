package com.zobgo.common.crawler.result.responsehandler;

import com.zobgo.common.crawler.entity.ErrorResponse;
import com.zobgo.common.crawler.exception.CrawlerTechnicalException;
import com.zobgo.common.crawler.spider.Page;
import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Task;
import com.zobgo.common.crawler.utils.HttpUtils;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by cw on 15-9-11.
 */
public class DefaultPdfResponseHandler extends AbstractResponseHandler {

    /**
     * Logger based on slf4j
     */
    private Logger logger = LoggerFactory.getLogger(DefaultPdfResponseHandler.class);

    @Override
    public Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws Exception {
        Page page = new Page();
        page.setHtml(parseContent(request, httpResponse));
        page.setOriginHtml(page.getHtml());
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setCookies(HttpUtils.getResponseCookies(httpResponse));
        request.setPage(page);
        request.setCookie(HttpUtils.getResponseCookies2String(httpResponse));
        request.getPage().setHeaders(httpResponse.getAllHeaders());
        request.putExtra("Location",httpResponse.getFirstHeader("Location") == null ? null : httpResponse.getFirstHeader("Location").getValue() );
        setResult(request);
        request.setIsSuccess(true);
        return page;
    }

    /**
     * 归一化返回值
     * @param request
     * @param httpResponse
     * @return
     * @throws Exception
     */
    public String parseContent(Request request,HttpResponse httpResponse)throws Exception {
        final InputStream inputStream = httpResponse.getEntity().getContent();
        //数据归一化序列化
        if ( request.getTargetTemplateProducter() != null && inputStream != null )
        {
            try {
                return request.getTargetTemplateProducter().setAsMapper(inputStream, request.getTargetTemplateProducter()).createTargetTemplate().getAsJson();
            }catch (CrawlerTechnicalException e) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.message = e.getMessage();
                request.setErrorResponse(errorResponse);
                throw e;
            }
        }
        return "无返回值";
    }

    @Override
    protected void setResult(Request request)throws Exception {
    }
}
