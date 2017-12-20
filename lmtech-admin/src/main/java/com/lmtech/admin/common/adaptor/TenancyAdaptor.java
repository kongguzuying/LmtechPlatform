package com.lmtech.admin.common.adaptor;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenancyAdaptor extends ServiceAdaptorBase implements ControllerManager<Tenancy> {
    @Autowired
    private TenancyFacade tenancyFacade;

    @Override
    public String add(Tenancy tenancy) {
        TenancyRequest request = new TenancyRequest();
        request.setReqData(tenancy);
        initRequest(request);

        StringResponse response = tenancyFacade.addTenancy(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(Tenancy tenancy) {
        TenancyRequest request = new TenancyRequest();
        request.setReqData(tenancy);
        initRequest(request);

        NormalResponse response = tenancyFacade.editTenancy(request);
        validResponse(response);
        return tenancy.getId();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest();
        request.setReqData(id);
        initRequest(request);

        NormalResponse response = tenancyFacade.removeTenancy(request);
        validResponse(response);
    }

    @Override
    public Tenancy get(String id) {
        StringRequest request = new StringRequest();
        request.setReqData(id);
        initRequest(request);

        TenancyResponse response = tenancyFacade.getTenancy(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<Tenancy> getAll() {
        return null;
    }

    @Override
    public PageData<Tenancy> getPageData(Tenancy param, int pageIndex, int pageSize) {
        TenancyPageRequest request = new TenancyPageRequest();
        TenancyQueryParam queryParam = new TenancyQueryParam();
        queryParam.setCode(param.getCode());
        request.setReqData(queryParam);
        initRequest(request);

        TenancyPageResponse response = tenancyFacade.getTenancyOfPage(request);
        validResponse(response);
        return response.getData();
    }
}
