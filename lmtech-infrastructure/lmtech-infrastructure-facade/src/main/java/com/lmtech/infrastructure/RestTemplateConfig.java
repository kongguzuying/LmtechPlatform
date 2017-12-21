package com.lmtech.infrastructure;

import com.lmtech.annotation.ConfigurationServerAll;
import com.lmtech.http.interceptor.LoggingRequestInterceptor;
import com.lmtech.util.RestTemplateBuilder;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

@ConfigurationServerAll
public class RestTemplateConfig {
    @Bean(name = "poolingHttpClientConnectionManager")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(100);
        //路由的最大连接数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);
        return poolingHttpClientConnectionManager;
    }

    @Bean
    public HttpRequestRetryHandler httpRequestRetryHandler() {
        return (exception, executionCount, context) -> {
            if (executionCount >= 3) {// 如果已经重试了5次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            //如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager());
        httpClientBuilder.setRetryHandler(httpRequestRetryHandler());
        return httpClientBuilder;
    }

    @Bean(name = "httpClient")
    public CloseableHttpClient httpClient() {
        return httpClientBuilder().build();
    }

    @Bean(name = "httpComponentsClientHttpRequestFactory")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(30000);
        return requestFactory;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate();
        LoggingRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(loggingRequestInterceptor);
        //启用连接池
        //restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory());
        return restTemplate;
    }
}
