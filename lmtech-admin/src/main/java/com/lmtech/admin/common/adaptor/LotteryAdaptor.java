package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.LotteryPageRequest;
import com.ea.card.crm.admin.facade.request.LotteryRequest;
import com.ea.card.crm.admin.facade.response.LotteryPageResponse;
import com.ea.card.crm.admin.facade.response.LotteryResponse;
import com.ea.card.crm.admin.facade.stub.LotteryFacade;
import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryAdaptor extends ServiceAdaptorBase implements ControllerManager<LotteryProduct> {

    @Autowired
    private LotteryFacade lotteryFacade;

    @Override
    public String add(LotteryProduct codeType) {
        LotteryRequest request = new LotteryRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = lotteryFacade.addLottery(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(LotteryProduct codeType) {
        LotteryRequest request = new LotteryRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = lotteryFacade.editLottery(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = lotteryFacade.removeLottery(request);
        validResponse(response);
    }

    @Override
    public LotteryProduct get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        LotteryResponse response = lotteryFacade.getLottery(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<LotteryProduct> getAll() {
        return null;
    }

    @Override
    public PageData<LotteryProduct> getPageData(LotteryProduct param, int pageIndex, int pageSize) {
        LotteryPageRequest request = new LotteryPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        LotteryPageResponse response = lotteryFacade.getLotteryOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
