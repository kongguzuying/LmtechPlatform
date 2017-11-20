package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.admin.facade.request.MemberRegisterPageRequest;
import com.ea.card.crm.admin.facade.request.MemberRegisterRequest;
import com.ea.card.crm.admin.facade.response.MemberRegisterPageResponse;
import com.ea.card.crm.admin.facade.response.MemberRegisterResponse;
import com.ea.card.crm.admin.facade.stub.MemberRegisterFacade;
import com.ea.card.crm.model.MemberRegister;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberRegisterAdaptor extends ServiceAdaptorBase implements ControllerManager<MemberRegister> {

    @Autowired
    private MemberRegisterFacade memberRegisterFacade;

    @Override
    public String add(MemberRegister codeType) {
        MemberRegisterRequest request = new MemberRegisterRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = memberRegisterFacade.addMemberRegister(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(MemberRegister codeType) {
        MemberRegisterRequest request = new MemberRegisterRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = memberRegisterFacade.editMemberRegister(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = memberRegisterFacade.removeMemberRegister(request);
        validResponse(response);
    }

    @Override
    public MemberRegister get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        MemberRegisterResponse response = memberRegisterFacade.getMemberRegister(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<MemberRegister> getAll() {
        return null;
    }

    @Override
    public PageData<MemberRegister> getPageData(MemberRegister param, int pageIndex, int pageSize) {
        MemberRegisterPageRequest request = new MemberRegisterPageRequest();

        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        MemberRegisterPageResponse response = memberRegisterFacade.getMemberRegisterOfPage(request);
        validResponse(response);
        return response.getData();
    }

}
