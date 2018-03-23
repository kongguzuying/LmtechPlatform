package com.lmtech.cloud.zuul.filter;

import com.lmtech.cloud.zuul.exception.NoneException;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Service
public class LoginFilter extends ZuulFilter {

    @Value("${ignore_url.ignore_url1}")
    private String ignoreUrl1;
    @Value("${ignore_url.ignore_url2}")
    private String ignoreUrl2;
    @Value("${ignore_url.ignore_url3}")
    private String ignoreUrl3;
    @Value("${ignore_url.ignore_url4}")
    private String ignoreUrl4;
    @Value("${ignore_url.ignore_url5}")
    private String ignoreUrl5;
    @Value("${ignore_url.ignore_url6}")
    private String ignoreUrl6;
    @Value("${ignore_url.ignore_url7}")
    private String ignoreUrl7;
    @Value("${ignore_url.ignore_url8}")
    private String ignoreUrl8;
    @Value("${ignore_url.ignore_url9}")
    private String ignoreUrl9;
    @Value("${ignore_url.ignore_url10}")
    private String ignoreUrl10;
    @Value("${ignore_url.ignore_url11}")
    private String ignoreUrl11;
    @Value("${ignore_url.ignore_url12}")
    private String ignoreUrl12;
    @Value("${ignore_url.ignore_url13}")
    private String ignoreUrl13;
    @Value("${ignore_url.ignore_url14}")
    private String ignoreUrl14;
    @Value("${ignore_url.ignore_url15}")
    private String ignoreUrl15;
    @Value("${ignore_url.ignore_url16}")
    private String ignoreUrl16;
    @Value("${ignore_url.ignore_url17}")
    private String ignoreUrl17;
    @Value("${ignore_url.ignore_url18}")
    private String ignoreUrl18;
    @Value("${ignore_url.ignore_url19}")
    private String ignoreUrl19;
    @Value("${ignore_url.ignore_url20}")
    private String ignoreUrl20;

    @Value("${service.url_istokenexpire}")
    private String URL_IS_TOKEN_EXPIRE;

    private Object lockObj = new Object();
    private List<String> ignoreUrls = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (CollectionUtil.isNullOrEmpty(ignoreUrls)) {
            synchronized (lockObj) {
                if (CollectionUtil.isNullOrEmpty(ignoreUrls)) {
                    if (!StringUtil.isNullOrEmpty(ignoreUrl1)) ignoreUrls.add(ignoreUrl1);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl2)) ignoreUrls.add(ignoreUrl2);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl3)) ignoreUrls.add(ignoreUrl3);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl4)) ignoreUrls.add(ignoreUrl4);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl5)) ignoreUrls.add(ignoreUrl5);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl6)) ignoreUrls.add(ignoreUrl6);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl7)) ignoreUrls.add(ignoreUrl7);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl8)) ignoreUrls.add(ignoreUrl8);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl9)) ignoreUrls.add(ignoreUrl9);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl10)) ignoreUrls.add(ignoreUrl10);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl11)) ignoreUrls.add(ignoreUrl11);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl12)) ignoreUrls.add(ignoreUrl12);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl13)) ignoreUrls.add(ignoreUrl13);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl14)) ignoreUrls.add(ignoreUrl14);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl15)) ignoreUrls.add(ignoreUrl15);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl16)) ignoreUrls.add(ignoreUrl16);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl17)) ignoreUrls.add(ignoreUrl17);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl18)) ignoreUrls.add(ignoreUrl18);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl19)) ignoreUrls.add(ignoreUrl19);
                    if (!StringUtil.isNullOrEmpty(ignoreUrl20)) ignoreUrls.add(ignoreUrl20);
                }
            }
        }
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String requestUri = request.getRequestURI();

        if (!CollectionUtil.isNullOrEmpty(ignoreUrls)) {
            for (String ignoreUrl : ignoreUrls) {
                if (!StringUtil.isNullOrEmpty(requestUri) && requestUri.contains(ignoreUrl)) {
                    return true;
                }
            }
        }

        String token = request.getHeader("AuthToken");
        if (!StringUtil.isNullOrEmpty(token)) {
            //校验token
            MultiValueMap<String, Object> validMap = new LinkedMultiValueMap<>();
            validMap.add("token", token);
            HttpEntity<MultiValueMap<String, Object>> validRequest = new HttpEntity<>(validMap, null);
            StateResult result = restTemplate.postForObject(URL_IS_TOKEN_EXPIRE, validRequest, StateResult.class);

            if (!result.isSuccess()) {
                //用户校验失败
                validFailed(response);
            }
        } else {
            //用户校验失败
            validFailed(response);
        }
        return null;
    }

    private void validFailed(HttpServletResponse response) {
        ExeResult result = new ExeResult();
        result.setSuccess(false);
        result.setMessage("用户未登录");
        try {
            response.setStatus(200);
            response.getWriter().write(JsonUtil.toJson(result.getResult()));
            response.getWriter().flush();
        } catch (IOException e) {
            LoggerManager.error(e);
        }
        ZuulException zuulException = new ZuulException(new NoneException(), HttpStatus.OK.value(), null);
        throw new ZuulRuntimeException(zuulException);
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public int filterOrder() {
        return 1;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }
}
