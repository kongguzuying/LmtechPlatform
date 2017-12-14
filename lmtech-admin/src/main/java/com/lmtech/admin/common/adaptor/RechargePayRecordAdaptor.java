package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.RechargePayRecordPageRequest;
import com.ea.card.crm.admin.facade.request.RechargePayRecordRequest;
import com.ea.card.crm.admin.facade.response.RechargePayRecordPageResponse;
import com.ea.card.crm.admin.facade.response.RechargePayRecordResponse;
import com.ea.card.crm.admin.facade.stub.RechargePayRecordFacade;
import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RechargePayRecordAdaptor extends ServiceAdaptorBase implements ControllerManager<RechargePayRecord> {

    @Autowired
    private RechargePayRecordFacade rechargePayRecordFacade;

    @Override
    public String add(RechargePayRecord codeType) {
        RechargePayRecordRequest request = new RechargePayRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = rechargePayRecordFacade.addRechargePayRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(RechargePayRecord codeType) {
        RechargePayRecordRequest request = new RechargePayRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = rechargePayRecordFacade.editRechargePayRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = rechargePayRecordFacade.removeRechargePayRecord(request);
        validResponse(response);
    }

    @Override
    public RechargePayRecord get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        RechargePayRecordResponse response = rechargePayRecordFacade.getRechargePayRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<RechargePayRecord> getAll() {
        return null;
    }

    @Override
    public PageData<RechargePayRecord> getPageData(RechargePayRecord param, int pageIndex, int pageSize) {
        RechargePayRecordPageRequest request = new RechargePayRecordPageRequest();

        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        RechargePayRecordPageResponse response = rechargePayRecordFacade.getRechargePayRecordOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
