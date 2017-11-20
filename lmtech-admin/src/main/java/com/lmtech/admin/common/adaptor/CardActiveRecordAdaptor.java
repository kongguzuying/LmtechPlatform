package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.CardActiveRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardActiveRecordRequest;
import com.ea.card.crm.admin.facade.response.CardActiveRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardActiveRecordResponse;
import com.ea.card.crm.admin.facade.stub.CardActiveRecordFacade;
import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardActiveRecordAdaptor extends ServiceAdaptorBase implements ControllerManager<CardActiveRecord> {

    @Autowired
    private CardActiveRecordFacade cardActiveRecordFacade;

    @Override
    public String add(CardActiveRecord codeType) {
        CardActiveRecordRequest request = new CardActiveRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardActiveRecordFacade.addCardActiveRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(CardActiveRecord codeType) {
        CardActiveRecordRequest request = new CardActiveRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardActiveRecordFacade.editCardActiveRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = cardActiveRecordFacade.removeCardActiveRecord(request);
        validResponse(response);
    }

    @Override
    public CardActiveRecord get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        CardActiveRecordResponse response = cardActiveRecordFacade.getCardActiveRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<CardActiveRecord> getAll() {
        return null;
    }

    @Override
    public PageData<CardActiveRecord> getPageData(CardActiveRecord param, int pageIndex, int pageSize) {
        CardActiveRecordPageRequest request = new CardActiveRecordPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        CardActiveRecordPageResponse response = cardActiveRecordFacade.getCardActiveRecordOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
