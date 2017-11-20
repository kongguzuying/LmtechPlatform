package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.GroupRequest;
import com.lmtech.infrastructure.facade.response.GroupListResponse;
import com.lmtech.infrastructure.facade.response.GroupResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 群组服务入口
 * Created by huang.jb on 2017-3-29.
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/group")
public interface GroupFacade {
    /**
     * 查询根群组
     * @return
     */
    @RequestMapping(value = "/queryRootGroup", method = RequestMethod.POST)
    GroupListResponse queryRootGroup(NormalRequest request);

    /**
     * 查询子群组
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryChildrenGroup", method = RequestMethod.POST)
    GroupListResponse queryChildrenGroup(StringRequest request);

    /**
     * 获取群组
     * @param request
     * @return
     */
    @RequestMapping(value = "/getGroup", method = RequestMethod.POST)
    GroupResponse getGroup(StringRequest request);

    /**
     * 添加群组
     * @param request
     * @return
     */
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    StringResponse addGroup(GroupRequest request);

    /**
     * 编辑群组
     * @param request
     * @return
     */
    @RequestMapping(value = "/editGroup", method = RequestMethod.POST)
    StringResponse editGroup(GroupRequest request);

    /**
     * 删除群组
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeGroup", method = RequestMethod.POST)
    NormalResponse removeGroup(StringRequest request);
}
