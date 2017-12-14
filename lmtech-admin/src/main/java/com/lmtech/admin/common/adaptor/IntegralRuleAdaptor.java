package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.IntegralRulePageRequest;
import com.ea.card.crm.admin.facade.request.IntegralRuleRequest;
import com.ea.card.crm.admin.facade.response.IntegralRulePageResponse;
import com.ea.card.crm.admin.facade.response.IntegralRuleResponse;
import com.ea.card.crm.admin.facade.stub.IntegralRuleFacade;
import com.ea.card.crm.model.IntegralRule;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralRuleAdaptor extends ServiceAdaptorBase implements ControllerManager<IntegralRule> {

    @Autowired
    private IntegralRuleFacade integralSetFacade;

    @Override
    public String add(IntegralRule codeType) {
        IntegralRuleRequest request = new IntegralRuleRequest();
        codeType.setStatus(IntegralRule.STATUS_ONE);
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = integralSetFacade.addIntegralRule(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(IntegralRule codeType) {
        IntegralRuleRequest request = new IntegralRuleRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = integralSetFacade.editIntegralRule(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = integralSetFacade.removeIntegralRule(request);
        validResponse(response);
    }

    @Override
    public IntegralRule get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        IntegralRuleResponse response = integralSetFacade.getIntegralRule(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<IntegralRule> getAll() {
        return null;
    }

    @Override
    public PageData<IntegralRule> getPageData(IntegralRule param, int pageIndex, int pageSize) {
        IntegralRulePageRequest request = new IntegralRulePageRequest();

        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        IntegralRulePageResponse response = integralSetFacade.getIntegralRuleOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
