package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.IntegralSetPageRequest;
import com.ea.card.crm.admin.facade.request.IntegralSetRequest;
import com.ea.card.crm.admin.facade.response.IntegralSetPageResponse;
import com.ea.card.crm.admin.facade.response.IntegralSetResponse;
import com.ea.card.crm.admin.facade.stub.IntegralSetFacade;
import com.ea.card.crm.model.IntegralSet;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralSetAdaptor extends ServiceAdaptorBase implements ControllerManager<IntegralSet> {

    @Autowired
    private IntegralSetFacade integralSetFacade;

    @Override
    public String add(IntegralSet codeType) {
        IntegralSetRequest request = new IntegralSetRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = integralSetFacade.addIntegralSet(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(IntegralSet codeType) {
        IntegralSetRequest request = new IntegralSetRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = integralSetFacade.editIntegralSet(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = integralSetFacade.removeIntegralSet(request);
        validResponse(response);
    }

    @Override
    public IntegralSet get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        IntegralSetResponse response = integralSetFacade.getIntegralSet(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<IntegralSet> getAll() {
        return null;
    }

    @Override
    public PageData<IntegralSet> getPageData(IntegralSet param, int pageIndex, int pageSize) {
        IntegralSetPageRequest request = new IntegralSetPageRequest();

        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        IntegralSetPageResponse response = integralSetFacade.getIntegralSetOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
