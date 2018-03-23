package com.lmtech.cloud.zuul.filter;

import com.lmtech.cloud.zuul.exception.NoneException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SendErrorExtFilter extends ZuulFilter {
    private static final Log log = LogFactory.getLog(SendErrorExtFilter.class);
    protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";
    @Value("${error.path:/error}")
    private String errorPath;

    public SendErrorExtFilter() {
    }

    public String filterType() {
        return "error";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getThrowable() != null && !ctx.getBoolean("sendErrorFilter.ran", false);
    }

    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            ZuulException exception = this.findZuulException(ctx.getThrowable());
            if (exception.getCause() instanceof NoneException) {
                //过滤无需处理异常
                log.error("test error");
                return null;
            }
            HttpServletRequest request = ctx.getRequest();
            request.setAttribute("javax.servlet.error.status_code", exception.nStatusCode);
            log.warn("Error during filtering", exception);
            request.setAttribute("javax.servlet.error.exception", exception);
            if (StringUtils.hasText(exception.errorCause)) {
                request.setAttribute("javax.servlet.error.message", exception.errorCause);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
            if (dispatcher != null) {
                ctx.set("sendErrorFilter.ran", true);
                if (!ctx.getResponse().isCommitted()) {
                    dispatcher.forward(request, ctx.getResponse());
                }
            }
        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }

        return null;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            return (ZuulException)throwable.getCause().getCause();
        } else if (throwable.getCause() instanceof ZuulException) {
            return (ZuulException)throwable.getCause();
        } else {
            return throwable instanceof ZuulException ? (ZuulException)throwable : new ZuulException(throwable, 500, (String)null);
        }
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }
}
