package com.lmtech.admin.common.web;

import com.lmtech.util.StringUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * request body读取过滤器
 * Created by huang.jb on 2016-8-10.
 */
public class BodyReaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.needRequestWrap(request)) {
            BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            filterChain.doFilter(requestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean needRequestWrap(HttpServletRequest httpServletRequest) throws IOException {
        boolean isJsonStream = !StringUtil.isNullOrEmpty(httpServletRequest.getContentType()) &&  httpServletRequest.getContentType().trim().indexOf("application/json") >= 0;
        return isJsonStream;
    }
}
