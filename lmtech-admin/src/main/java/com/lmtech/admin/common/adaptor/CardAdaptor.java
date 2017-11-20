package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.CardPageRequest;
import com.ea.card.crm.admin.facade.request.CardRequest;
import com.ea.card.crm.admin.facade.response.CardListResponse;
import com.ea.card.crm.admin.facade.response.CardPageResponse;
import com.ea.card.crm.admin.facade.response.CardResponse;
import com.ea.card.crm.admin.facade.stub.CardCategoryFacade;
import com.ea.card.crm.model.CardCategory;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.BooleanResponse;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardAdaptor extends ServiceAdaptorBase implements ControllerManager<CardCategory> {

    @Autowired
    private CardCategoryFacade cardCategoryFacade;

    @Override
    public String add(CardCategory codeType) {
        CardRequest request = new CardRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardCategoryFacade.addCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(CardCategory codeType) {
        CardRequest request = new CardRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = cardCategoryFacade.editCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = cardCategoryFacade.removeCard(request);
        validResponse(response);
    }

    @Override
    public CardCategory get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        CardResponse response = cardCategoryFacade.getCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<CardCategory> getAll() {
        NormalRequest request = new NormalRequest();
        initRequest(request);

        CardListResponse response = cardCategoryFacade.getAllCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public PageData<CardCategory> getPageData(CardCategory param, int pageIndex, int pageSize) {
        CardPageRequest request = new CardPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        CardPageResponse response = cardCategoryFacade.getCardOfPage(request);
        validResponse(response);
        return response.getData();
    }

    public boolean existTitleName(String title) {
        StringRequest request = new StringRequest(title);
        initRequest(request);

        BooleanResponse response = cardCategoryFacade.existTitleName(request);
        validResponse(response);
        return response.getData();
    }

}
