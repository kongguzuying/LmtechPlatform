package com.lmtech.admin.common.interceptors;

import com.lmtech.auth.model.AccountInfo;
import com.lmtech.common.Context;
import com.lmtech.common.ContextManager;
import com.lmtech.infrastructure.model.User;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 登录拦截
 * Created by huang.jb on 2016-7-7.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private List<String> ignoreUrls = new ArrayList<String>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();

        //过滤地址
        if (isIgnoreUrl(requestUri)) {
            return true;
        }

        User user = (User) request.getSession().getAttribute("cur_user");
        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/page/platform/portal/login.jsp").forward(request, response);
            return false;
        } else {
            AccountInfo account = (AccountInfo) request.getSession().getAttribute("cur_account");
            String token = (String) request.getSession().getAttribute("LmToken");
            Context context = new Context();
            context.setUserId(user.getId());
            context.setUserName(user.getNickName());
            context.setAccountId(account.getAccountId());
            context.setAccountName(account.getAccountName());
            context.setLoginName(account.getLoginName());
            context.setGroupId(user.getGroupId());
            context.setTenancyId(user.getTenancyId());
            context.setToken(token);
            ContextManager.setContext(context);

            return true;
        }
    }

    private boolean isIgnoreUrl(String requestUri) {
        if (!CollectionUtil.isNullOrEmpty(ignoreUrls)) {
            for (String ignoreUrl : ignoreUrls) {
                if (!StringUtil.isNullOrEmpty(requestUri) && requestUri.contains(ignoreUrl)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
