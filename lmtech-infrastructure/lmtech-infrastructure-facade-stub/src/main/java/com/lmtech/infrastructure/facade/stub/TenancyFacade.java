package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.response.TenancyPageRequest;
import com.lmtech.infrastructure.facade.response.TenancyPageResponse;
import com.lmtech.infrastructure.facade.response.TenancyRequest;
import com.lmtech.infrastructure.facade.response.TenancyResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 租户服务入口
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/tenancy")
public interface TenancyFacade {
    /**
     * 获取租户
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTenancy", method = RequestMethod.POST)
    TenancyResponse getTenancy(StringRequest request);

    /**
     * 通过唯一code码获取租户
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTenancyByCode", method = RequestMethod.POST)
    TenancyResponse getTenancyByCode(StringRequest request);

    /**
     * 获取租户分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTenancyOfPage", method = RequestMethod.POST)
    TenancyPageResponse getTenancyOfPage(TenancyPageRequest request);

    /**
     * 添加租户
     * @param request
     * @return
     */
    @RequestMapping(value = "/addTenancy", method = RequestMethod.POST)
    StringResponse addTenancy(TenancyRequest request);

    /**
     * 编辑租户
     * @param request
     * @return
     */
    @RequestMapping(value = "/editTenancy", method = RequestMethod.POST)
    NormalResponse editTenancy(TenancyRequest request);

    /**
     * 删除租户
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeTenancy", method = RequestMethod.POST)
    NormalResponse removeTenancy(StringRequest request);

    /**
     * 开始营业
     * @param request
     */
    NormalResponse activeTenancy(StringRequest request);

    /**
     * 停止营业
     * @param request
     */
    NormalResponse disableTenancy(StringRequest request);
}
