package com.zobgo.common.crawler.utils;

import com.google.gson.Gson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zobgo.common.crawler.config.Config;
import com.zobgo.common.crawler.entity.ErrorResponse;
import com.zobgo.common.crawler.proxy.HttpProxy;
import com.zobgo.common.crawler.result.responsehandler.IResponseValidator;
import com.zobgo.common.crawler.spider.Request;
import com.zobgo.common.crawler.spider.Site;
import com.zobgo.common.crawler.spider.Spider;
import com.zogbo.common.utils.HttpConstant;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pooled Proxy Object
 *
 */

public class HttpProxyUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpProxyUtil.class);
	public static HttpProxy getHttpProxy(String key){
		final String value = RedisPool.get("proxy_for_operator");

		try {
			Pattern pattern = Pattern.compile("1[0-9]{10}");
			Matcher matcher = pattern.matcher(key);
			if (matcher.find()) {
				final String flag = RedisPool.get(key);
				if (flag != null) {
					logger.info("NOT USE PROXY");
					return null;
				}
			}
		}catch (Exception e){
			logger.error("From Redis Get Proxy Control Failed",e);
		}

		if (value == null){
			HttpProxy httpProxy = newAdsl();
			return httpProxy == null || !httpProxy.validate() ?  httpProxy() : httpProxy;
		}else if (value.equalsIgnoreCase("adsl")) {
			return adsl();
		}else if (value.equalsIgnoreCase("hp")){
			return httpProxy();
		}else if (value.equalsIgnoreCase("new_adsl")){
			HttpProxy httpProxy = newAdsl();
			return httpProxy == null || !httpProxy.validate() ?  httpProxy() : httpProxy;
		}else if (value.equalsIgnoreCase("new_old")){
			HttpProxy httpProxy = newAdsl();
			return httpProxy == null || !httpProxy.validate() ?  adsl() : httpProxy;
		}else if (value.equalsIgnoreCase("old_new")){
			HttpProxy httpProxy = adsl();
			return httpProxy == null || !httpProxy.validate() ?  newAdsl() : httpProxy;
		}

		HttpProxy httpProxy = newAdsl();

		httpProxy = httpProxy == null || !httpProxy.validate() ? adsl() : httpProxy;

		return httpProxy == null || !httpProxy.validate() ?  httpProxy() : httpProxy;
	}

	/**
	 * http://139.129.87.40:8080/center-controller-cluster-http/reptile/getProxy.shtml?dataSource=operator
	 * @return
     */
	private static HttpProxy adsl(){
		final String result = getProxyString(Config.getString("old_proxy_url"),null);

		if (result == null){
			return new HttpProxy();
		}

		HttpProxy httpProxy = new Gson().fromJson(result, HttpProxy.class);
		httpProxy.setUsername("wecash");

		return httpProxy;
	}

	/**
	 * http://139.129.87.40:8080/center-controller-cluster-http/reptile/getProxy.shtml?dataSource=operator
	 * @return
	 */
	private static HttpProxy newAdsl(){

		JSONObject body = new JSONObject();
		body.put("userName", Config.getString("proxy.pool.username"));
		body.put("password", Config.getString("proxy.pool.password"));
		body.put("webType", Config.getString("proxy.pool.webtype"));

		final String result = getProxyString(Config.getString("new_proxy_url"),body.toJSONString());

		if (result == null){
			return new HttpProxy();
		}
		JSONObject json = JSON.parseObject(result);
		HttpProxy httpProxy = new HttpProxy();
		httpProxy.setIp(json.getString("ip"));
		httpProxy.setPort(json.getInteger("squid_port"));
		httpProxy.setUsername(json.containsKey("name") ? json.getString("name").trim() : null);
		httpProxy.setPassword(json.getString("squid_password"));
		return httpProxy;
	}

	private static HttpProxy httpProxy(){
		final String result = getProxyString(Config.getString("proxy_url"),null);

		if (result == null){
			return new HttpProxy();
		}

		return new Gson().fromJson(result, HttpProxy.class);
	}


	private static String getProxyString(final String url,final String body){
		logger.info("start get proxy");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Request request = null;
		try{
			request = new Request(url);
			Site site = new Site();
			site.setTimeOut(3000);
			site.setRetryTimes(2);
			request.setSite(site);
			if (StringUtils.isEmpty(body)) {
				request.setMethod(com.zogbo.common.utils.HttpConstant.Method.GET);
			}else {
				request.setMethod(com.zogbo.common.utils.HttpConstant.Method.POST);
				request.getSite().setBody(body);
				request.getSite().setHeaders(new ArrayList<Header>(){{
					add(new BasicHeader("Content-Type","application/json"));
					add(new BasicHeader("User-Agent", com.zogbo.common.utils.UserAgents.getUserAgent(5)));
				}});
			}
			request.getResponseHandler().setResponseValidator(new IResponseValidator() {
				@Override
				public boolean validate(Request request) throws Exception {
					JSONObject json = JSON.parseObject(request.getPage().getOriginHtml());
					final String ip = json.getString("ip");
					try{
						InetAddress addr= InetAddress.getByName(ip);
						if(addr.isReachable(2000)){
							logger.info("成功获取代理 ip = " + ip);
							return true;
						}
					}catch(Exception ex){
						logger.error("代理ip超时,切换代理",ex);
					}
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.message = "代理ip不可达,重试获取新的代理ip";
					errorResponse.messageForClient = "代理ip不可达,重试获取新的代理ip";
					request.setErrorResponse(errorResponse);
					logger.info("失败获取代理 ip = " + ip);
					return false;
				}
			});
			Spider.create().addRequest(request).run();
			request.validatorException();
			logger.info(request.getPage().getOriginHtml());

		}catch (Exception e){
			logger.error("get proxy error",e);
		}finally {
			stopWatch.stop();
			logger.info(String.format("finally get proxy = %s 消耗时间%s(ms)",request == null || !request.isSuccess() ? null : request.getPage().getOriginHtml(),stopWatch.getTime()));
		}
		return request == null || !request.isSuccess() ? null : request.getPage().getOriginHtml();
	}

	public static void main(String[] args){
//		HttpProxyUtil.newAdsl();


		Request request = new Request("http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=ip&rsv_pq=df3f2fe200004c61&rsv_t=6ef3fx3oacHs0FJOpiYwmBZHY7St90A9v1O%2BAZHsCp1pNh5drIE64y6yL%2Fo&rsv_enter=1&rsv_sug3=3&rsv_sug2=0&inputT=445&rsv_sug4=1120");
		request.setSite(new Site());
		request.getSite().setHttpProxy(HttpProxyUtil.getHttpProxy(""));
		request.setMethod(HttpConstant.Method.GET);
		Spider.create().addRequest(request).run();
	}

}
