package com.lmtech.admin.common.adaptor;

import com.lmtech.common.ContextManager;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignServiceRedirectInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            String tenancyCode = ContextManager.getContext().getTenancyCode();
            String serviceName = ContextManager.getValue("service_name");
            if (needRedirect(tenancyCode, serviceName)) {
                //租户调用接口，动态切换租户服务。租户服务：服务名-tenancyCode
                StringBuilder sb = new StringBuilder();
                String serviceUrl = ContextManager.getValue("service_url");
                sb.append(serviceUrl);
                sb.append("-" + tenancyCode);
                sb.append(requestTemplate.url());
                ClassUtil.setFieldValueIfExist(requestTemplate, "url", sb);

                LoggerManager.debug("租户：" + tenancyCode + "调用服务地址：" + requestTemplate.url());
            }
        } catch (Exception e) {
            LoggerManager.error(e);
        }
    }

    private boolean needRedirect(String tenancyCode, String serviceName) {
        //TODO 加入列表过滤，或者配置
        return !StringUtil.isNullOrEmpty(tenancyCode)
                && !StringUtil.isNullOrEmpty(serviceName)
                && (serviceName.equals("ea-card-crm") || serviceName.equals("ea-card-crm-admin"));

    }
}
