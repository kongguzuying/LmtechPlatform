package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.CardPresentRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardPresentRecordRequest;
import com.ea.card.crm.admin.facade.response.CardPresentRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardPresentRecordResponse;
import com.ea.card.crm.admin.facade.stub.CardPresentRecordFacade;
import com.ea.card.crm.model.CardPresentRecord;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardPresentRecordAdaptor extends ServiceAdaptorBase implements ControllerManager<CardPresentRecord> {

    @Autowired
    private CardPresentRecordFacade cardPresentRecordFacade;

    @Override
    public String add(CardPresentRecord codeType) {
        CardPresentRecordRequest request = new CardPresentRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardPresentRecordFacade.addCardPresentRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(CardPresentRecord codeType) {
        CardPresentRecordRequest request = new CardPresentRecordRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardPresentRecordFacade.editCardPresentRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = cardPresentRecordFacade.removeCardPresentRecord(request);
        validResponse(response);
    }

    @Override
    public CardPresentRecord get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        CardPresentRecordResponse response = cardPresentRecordFacade.getCardPresentRecord(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<CardPresentRecord> getAll() {
        return null;
    }

    @Override
    public PageData<CardPresentRecord> getPageData(CardPresentRecord param, int pageIndex, int pageSize) {
        CardPresentRecordPageRequest request = new CardPresentRecordPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        CardPresentRecordPageResponse response = cardPresentRecordFacade.getCardPresentRecordOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
