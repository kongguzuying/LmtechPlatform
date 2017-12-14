package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.CardPayRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardPayRecordRequest;
import com.ea.card.crm.admin.facade.response.CardPayRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardPayRecordResponse;
import com.ea.card.crm.admin.facade.stub.CardPayRecordFacade;
import com.ea.card.crm.model.CardPayRecord;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardPayRecordAdaptor extends ServiceAdaptorBase implements ControllerManager<CardPayRecord> {

    @Autowired
    private CardPayRecordFacade cardPayRecordFacade;

    @Override
    public String add(CardPayRecord codeType) {
        CardPayRecordRequest request = new CardPayRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardPayRecordFacade.addCardPayRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(CardPayRecord codeType) {
        CardPayRecordRequest request = new CardPayRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardPayRecordFacade.editCardPayRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = cardPayRecordFacade.removeCardPayRecord(request);
        validResponse(response);
    }

    @Override
    public CardPayRecord get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        CardPayRecordResponse response = cardPayRecordFacade.getCardPayRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<CardPayRecord> getAll() {
        return null;
    }

    @Override
    public PageData<CardPayRecord> getPageData(CardPayRecord param, int pageIndex, int pageSize) {
        CardPayRecordPageRequest request = new CardPayRecordPageRequest();

        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        CardPayRecordPageResponse response = cardPayRecordFacade.getCardPayRecordOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
