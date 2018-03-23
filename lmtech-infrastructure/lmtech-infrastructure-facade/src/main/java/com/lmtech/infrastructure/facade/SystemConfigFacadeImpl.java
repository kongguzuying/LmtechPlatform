package com.lmtech.infrastructure.facade;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.PageDataResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.SystemConfigPageRequest;
import com.lmtech.infrastructure.facade.request.SystemConfigRequest;
import com.lmtech.infrastructure.facade.response.SystemConfigResponse;
import com.lmtech.infrastructure.facade.stub.SystemConfigFacade;
import com.lmtech.infrastructure.model.SystemConfig;
import com.lmtech.infrastructure.service.SystemConfigManager;
import com.lmtech.infrastructure.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang.jb on 2017-1-11.
 */
@RestController
@RequestMapping(value = "/sysconfig")
@Api(description = "系统参数API入口")
public class SystemConfigFacadeImpl implements SystemConfigFacade {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemConfigManager systemConfigManager;

    @Override
    @RequestMapping(value = "/querySystemConfig", method = RequestMethod.POST)
    @ApiOperation(value = "查询系统参数值")
    public SystemConfigResponse querySystemConfig(@RequestBody StringRequest request) {
        String systemConfigCode = request.getReqData();

        SystemConfig systemConfig = systemConfigService.getSystemConfig(systemConfigCode);

        SystemConfigResponse result = new SystemConfigResponse();
        result.setSuccess(true);
        result.setData(systemConfig);
        return result;
    }

    @Override
    @RequestMapping(value = "/getSystemConfigOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取系统参数分页数据")
    public PageDataResponse getSystemConfigOfPage(@RequestBody SystemConfigPageRequest request) {
        Map<Object, Object> param = new HashMap<Object, Object>();
        PageData pageData = systemConfigManager.getPageData(param, request.getPageIndex(), request.getPageSize());

        PageDataResponse response = new PageDataResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取系统参数分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getSystemConfig", method = RequestMethod.POST)
    @ApiOperation(value = "获取系统参数")
    public SystemConfigResponse getSystemConfig(@RequestBody StringRequest request) {
        String code = request.getReqData();

        SystemConfig systemConfig = systemConfigManager.get(code);

        SystemConfigResponse response = new SystemConfigResponse();
        response.setSuccess(true);
        response.setData(systemConfig);
        response.setMessage("获取系统参数成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addSystemConfig", method = RequestMethod.POST)
    @ApiOperation(value = "添加系统参数")
    public StringResponse addSystemConfig(@RequestBody SystemConfigRequest request) {
        SystemConfig systemConfig = request.getReqData();

        String id = systemConfigManager.add(systemConfig);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setData(id);
        response.setMessage("添加系统参数成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editSystemConfig", method = RequestMethod.POST)
    @ApiOperation(value = "编辑系统参数")
    public NormalResponse editSystemConfig(@RequestBody SystemConfigRequest request) {
        SystemConfig systemConfig = request.getReqData();
        Assert.notNull(systemConfig.getCode(), "传入系统参数Code不允许为空");

        systemConfigManager.edit(systemConfig);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("编辑系统参数成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeSystemConfig", method = RequestMethod.POST)
    @ApiOperation(value = "删除系统参数")
    public NormalResponse removeSystemConfig(@RequestBody StringRequest request) {
        String code = request.getReqData();

        systemConfigManager.remove(code);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除系统参数成功");
        return response;
    }
}
