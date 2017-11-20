package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.PageDataResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.SystemConfigPageRequest;
import com.lmtech.infrastructure.facade.request.SystemConfigRequest;
import com.lmtech.infrastructure.facade.response.SystemConfigResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 系统配置服务入口
 * Created by huang.jb on 2017-1-11.
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/sysconfig")
public interface SystemConfigFacade {
    /**
     * 查询系统参数值
     * @param request
     * @return
     */
    @RequestMapping(value = "/querySystemConfig", method = RequestMethod.POST)
    SystemConfigResponse querySystemConfig(StringRequest request);

    /**
     * 获取系统参数分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSystemConfigOfPage", method = RequestMethod.POST)
    PageDataResponse getSystemConfigOfPage(SystemConfigPageRequest request);

    /**
     * 获取系统参数
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSystemConfig", method = RequestMethod.POST)
    SystemConfigResponse getSystemConfig(StringRequest request);

    /**
     * 添加系统参数
     * @param request
     * @return
     */
    @RequestMapping(value = "/addSystemConfig", method = RequestMethod.POST)
    StringResponse addSystemConfig(SystemConfigRequest request);

    /**
     * 编辑系统参数
     * @param request
     * @return
     */
    @RequestMapping(value = "/editSystemConfig", method = RequestMethod.POST)
    NormalResponse editSystemConfig(SystemConfigRequest request);

    /**
     * 删除系统参数
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeSystemConfig", method = RequestMethod.POST)
    NormalResponse removeSystemConfig(StringRequest request);
}
