package com.lmtech.infrastructure.facade;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.dto.TenancyQueryParam;
import com.lmtech.infrastructure.facade.response.TenancyPageRequest;
import com.lmtech.infrastructure.facade.response.TenancyPageResponse;
import com.lmtech.infrastructure.facade.response.TenancyRequest;
import com.lmtech.infrastructure.facade.response.TenancyResponse;
import com.lmtech.infrastructure.facade.stub.TenancyFacade;
import com.lmtech.infrastructure.model.Tenancy;
import com.lmtech.infrastructure.service.TenancyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tenancy")
@Api(description = "租户API入口")
public class TenancyFacadeImpl implements TenancyFacade {

    @Autowired
    private TenancyService tenancyService;

    @Override
    public TenancyResponse getTenancy(StringRequest request) {
        String tenancyId = request.getReqData();

        Tenancy tenancy = tenancyService.get(tenancyId);

        TenancyResponse response = new TenancyResponse();
        response.setSuccess(true);
        response.setData(tenancy);
        response.setMessage("获取租户信息成功。");
        return response;
    }

    @Override
    public TenancyResponse getTenancyByCode(StringRequest request) {
        String tenancyCode = request.getReqData();

        Tenancy tenancy = tenancyService.getByCode(tenancyCode);

        TenancyResponse response = new TenancyResponse();
        response.setSuccess(true);
        response.setData(tenancy);
        response.setMessage("获取租户信息成功。");
        return response;
    }

    @Override
    public TenancyPageResponse getTenancyOfPage(TenancyPageRequest request) {
        TenancyQueryParam queryParam = request.getReqData();
        Tenancy param = new Tenancy();
        param.setCode(queryParam.getCode());

        PageData<Tenancy> tenancyPageData = tenancyService.getPageData(param, request.getPageIndex(), request.getPageSize());

        TenancyPageResponse response = new TenancyPageResponse();
        response.setSuccess(true);
        response.setData(tenancyPageData);
        response.setMessage("获取租户分页数据成功。");
        return response;
    }

    @Override
    public StringResponse addTenancy(TenancyRequest request) {
        Tenancy tenancy = request.getReqData();
        tenancyService.add(tenancy);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setData(tenancy.getId());
        response.setMessage("添加租户数据成功。");
        return response;
    }

    @Override
    public NormalResponse editTenancy(TenancyRequest request) {
        tenancyService.edit(request.getReqData());

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("编辑租户数据成功。");
        return response;
    }

    @Override
    public NormalResponse removeTenancy(StringRequest request) {
        tenancyService.remove(request.getReqData());

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除租户数据成功。");
        return response;
    }

    @Override
    public NormalResponse activeTenancy(StringRequest request) {
        tenancyService.activeTenancy(request.getReqData());

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("租户开启营业成功。");
        return response;
    }

    @Override
    public NormalResponse disableTenancy(StringRequest request) {
        tenancyService.disableTenancy(request.getReqData());

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("租户停止营业成功。");
        return response;
    }
}
