package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.GiftMemberCardPageRequest;
import com.ea.card.crm.admin.facade.request.GiftMemberCardRequest;
import com.ea.card.crm.admin.facade.response.GiftMemberCardPageResponse;
import com.ea.card.crm.admin.facade.response.GiftMemberCardResponse;
import com.ea.card.crm.admin.facade.stub.GiftMemberCardFacade;
import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftMemberCardAdaptor extends ServiceAdaptorBase implements ControllerManager<GiftMemberCard> {

    @Autowired
    private GiftMemberCardFacade giftMemberCardFacade;

    @Override
    public String add(GiftMemberCard codeType) {
        GiftMemberCardRequest request = new GiftMemberCardRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = giftMemberCardFacade.addGiftMemberCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(GiftMemberCard codeType) {
        GiftMemberCardRequest request = new GiftMemberCardRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = giftMemberCardFacade.editGiftMemberCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = giftMemberCardFacade.removeGiftMemberCard(request);
        validResponse(response);
    }

    @Override
    public GiftMemberCard get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        GiftMemberCardResponse response = giftMemberCardFacade.getGiftMemberCard(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<GiftMemberCard> getAll() {
        return null;
    }

    @Override
    public PageData<GiftMemberCard> getPageData(GiftMemberCard param, int pageIndex, int pageSize) {
        GiftMemberCardPageRequest request = new GiftMemberCardPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        GiftMemberCardPageResponse response = giftMemberCardFacade.getGiftMemberCardOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
