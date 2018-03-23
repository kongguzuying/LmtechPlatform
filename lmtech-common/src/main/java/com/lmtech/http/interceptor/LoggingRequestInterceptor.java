package com.lmtech.http.interceptor;

import com.lmtech.http.BufferingClientHttpResponseWrapper;
import com.lmtech.util.LoggerManager;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 日志记录拦截器
 * @author huang.jb
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long startTime = System.currentTimeMillis();
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        ClientHttpResponse traceResponse = traceResponse(response);
        long endTime = System.currentTimeMillis();
        double useTime = ((double) (endTime - startTime) / 1000);
        LoggerManager.debug("use time: " + useTime + " seconds");
        return traceResponse;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        LoggerManager.debug("===========================http begin=============================================");
        LoggerManager.debug(String.format("URI         : %1$s", request.getURI()));
        LoggerManager.debug(String.format("Method      : %1$s", request.getMethod()));
        LoggerManager.debug(String.format("Headers     : %1$s", request.getHeaders()));
        LoggerManager.debug(String.format("Request body: %1$s", new String(body, "UTF-8")));
        LoggerManager.debug("----------------------------------------------------------------------------------");
    }

    private ClientHttpResponse traceResponse(ClientHttpResponse response) throws IOException {
        final ClientHttpResponse responseCopy = new BufferingClientHttpResponseWrapper(response);
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseCopy.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        LoggerManager.debug(String.format("Status code  : %1$s", response.getStatusCode()));
        LoggerManager.debug(String.format("Status text  : %1$s", response.getStatusText()));
        LoggerManager.debug(String.format("Headers      : %1$s", response.getHeaders()));
        LoggerManager.debug(String.format("Response body: %1$s", inputStringBuilder.toString()));
        LoggerManager.debug("===========================http end===============================================");

        return responseCopy;
    }

}
