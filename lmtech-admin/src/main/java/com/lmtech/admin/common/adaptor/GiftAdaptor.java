package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.GiftPageRequest;
import com.ea.card.crm.admin.facade.request.GiftRequest;
import com.ea.card.crm.admin.facade.response.GiftPageResponse;
import com.ea.card.crm.admin.facade.response.GiftResponse;
import com.ea.card.crm.admin.facade.stub.GiftCategoryFacade;
import com.ea.card.crm.model.GiftCategory;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftAdaptor extends ServiceAdaptorBase implements ControllerManager<GiftCategory> {

    @Autowired
    private GiftCategoryFacade giftCategoryFacade;

    @Override
    public String add(GiftCategory codeType) {
        GiftRequest request = new GiftRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = giftCategoryFacade.addGift(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(GiftCategory codeType) {
        GiftRequest request = new GiftRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = giftCategoryFacade.editGift(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = giftCategoryFacade.removeGift(request);
        validResponse(response);
    }

    @Override
    public GiftCategory get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        GiftResponse response = giftCategoryFacade.getGift(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<GiftCategory> getAll() {
        return null;
    }

    @Override
    public PageData<GiftCategory> getPageData(GiftCategory param, int pageIndex, int pageSize) {
        GiftPageRequest request = new GiftPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        GiftPageResponse response = giftCategoryFacade.getGiftOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
