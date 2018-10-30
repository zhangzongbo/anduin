package com.zobgo.common.crawler.result.responsehandler;

import com.alibaba.fastjson.JSONObject;
import com.zobgo.common.crawler.entity.ErrorResponse;
import com.zobgo.common.crawler.exception.CrawlerBusinessException;
import com.zobgo.common.crawler.exception.CrawlerTechnicalException;
import com.zobgo.common.crawler.exception.CrawlerValidatorException;
import com.zobgo.common.crawler.spider.Page;
import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Task;
import com.zobgo.common.crawler.utils.ChineseMessyCode;
import com.zobgo.common.crawler.utils.HttpUtils;
import com.zogbo.common.utils.UrlUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.codecraft.webmagic.selector.Html;

/**
 * Created by cw on 15-9-11.
 */
public abstract class AbstractResponseHandler {
    private Logger logger = LoggerFactory.getLogger(AbstractResponseHandler.class);
    protected IResponseValidator responseValidator = new DefaultResponseValidator();
    public Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws Exception
    {
        if (charset == null)
        {
            charset = Consts.UTF_8.displayName();
        }
        String content = getContent(charset, httpResponse);
        //logger.info(content);
        Page page = new Page();
        page.setOriginHtml(content);
        page.setHtml(content);
//        logger.info("htmlhtmlhtml" +Html.create(UrlUtils.fixAllRelativeHrefs(content, request.getUrl())));
        page.setCorehtml(Html.create(UrlUtils.fixAllRelativeHrefs(content, request.getUrl())));
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setCookies(HttpUtils.getResponseCookies(httpResponse));

        //将page中的corehtml带入到templateProducter中
        if(request.getTargetTemplateProducter() != null&& page.getCorehtml( ) != null) {
            request.getTargetTemplateProducter().setHtml(page.getCorehtml());
        }
        request.setPage(page);
        request.setCookie(HttpUtils.getResponseCookies2String(httpResponse));
        request.getPage().setHeaders(httpResponse.getAllHeaders());
        request.putExtra("Location",httpResponse.getFirstHeader("Location") == null ? null : httpResponse.getFirstHeader("Location").getValue() );
        if (!validate(request))
        {//如果校验不通过
            throw new CrawlerValidatorException("校验不通过","校验异常");
        }

        page.setHtml(parseContent(request, content));
        setResult(request);
        request.setIsSuccess(true);
        return page;
    }
    protected abstract void setResult(Request request) throws Exception;

    /**
     * 归一化返回值
     * @param request
     * @param content
     * @return
     * @throws Exception
     */
    public String parseContent(Request request,String content)throws Exception
    {
        //数据归一化序列化
        if ( request.getTargetTemplateProducter() != null && content != null )
        {
            try {
                return request.getTargetTemplateProducter().setAsMapper(content, request.getTargetTemplateProducter()).createTargetTemplate().getAsJson();
            }catch (CrawlerTechnicalException e)
            {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.message = e.getMessage();
                request.setErrorResponse(errorResponse);
                throw e;
            }
        }
        return content;
    }
    public boolean validate(Request request) throws Exception
    {
        /**
         * 乱码校验
         */
        if (request.getSite().isNeedMessyCodeValidate()) {
            logger.info(String.format("start validate messy code for key = %s", request.getKey()));
            /**
             * 如果返回网页中包含乱码
             */
            if (StringUtils.isNotEmpty(request.getPage().getOriginHtml())) {
                JSONObject jsonObject = JSONObject.parseObject(request.getPage().getOriginHtml());
                if (jsonObject.containsKey("pageMap")) {
                    if (ChineseMessyCode.isMessyCode(jsonObject.getString("pageMap"))) {
                        throw new CrawlerBusinessException(String.format("============== 数据获取乱码,key = %s ==============", request.getKey()), "数据获取内容错误");
                    }
                }
                logger.info(String.format("end validate messy code for key = %s", request.getKey()));
            }
        }

        return responseValidator.validate(request);
    }
    public IResponseValidator getResponseValidator() {
        return responseValidator;
    }

    public AbstractResponseHandler setResponseValidator(IResponseValidator responseValidator) {
        this.responseValidator = responseValidator;
        return this;
    }

    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        String result = null;
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                result = new String(contentBytes, htmlCharset);
            } else {
                result = new String(contentBytes);
            }
        } else {
            result =  IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
        return result;
    }
    protected boolean isHtml(String content)
    {
        try
        {
            Pattern pattern = Pattern.compile("<(S*?)[^>]*>.*?|<.*? />");
            Matcher matcher = pattern.matcher(content);
            if ( matcher.matches() )
            {
                return true;
            }
        }catch (Exception e)
        {
            logger.error("parse html failed ",e);
        }
        return false;
    }
    protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String charset;
        // charset
        // 1、encoding in http header Content-Type
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        // 2、charset in meta
        if (StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }
    protected String formatCookies(List<Cookie> cookies) {
        StringBuilder sb = new StringBuilder();
        for (Cookie c : cookies) {
            if (!"".equals(sb.toString())) {
                sb.append(" ");
            }
            sb.append(c.getName());
            sb.append("=");
            sb.append(c.getValue());
            sb.append(";");
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getAsJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String result;
        try {
            result = mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new CrawlerTechnicalException("Java对象序列化到json发生异常", "系统暂时不可用，请稍后重试", e);
        }

        return result;
    }
}