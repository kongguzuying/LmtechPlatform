package com.lmtech.admin.common.adaptor;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.GroupRequest;
import com.lmtech.infrastructure.facade.response.GroupListResponse;
import com.lmtech.infrastructure.facade.response.GroupResponse;
import com.lmtech.infrastructure.facade.stub.GroupFacade;
import com.lmtech.infrastructure.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 群组服务适配器
 * Created by huang.jb on 2017-3-29.
 */
@Service
public class GroupAdaptor extends ServiceAdaptorBase implements ControllerManager<Group> {

    @Autowired
    private GroupFacade groupFacade;

    @Override
    public String add(Group group) {
        GroupRequest request = new GroupRequest();
        request.setReqData(group);
        initRequest(request);

        StringResponse response = groupFacade.addGroup(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(Group group) {
        GroupRequest request = new GroupRequest();
        request.setReqData(group);
        initRequest(request);

        StringResponse response = groupFacade.editGroup(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        NormalResponse response = groupFacade.removeGroup(request);
        validResponse(response);
    }

    @Override
    public Group get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        GroupResponse response = groupFacade.getGroup(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<Group> getAll() {
        return null;
    }

    @Override
    public PageData getPageData(Group param, int pageIndex, int pageSize) {
        return null;
    }

    /**
     * 获取根群组
     * @return
     */
    public List<Group> getRootGroup() {
        NormalRequest request = new NormalRequest();
        initRequest(request);

        GroupListResponse response = groupFacade.queryRootGroup(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取子群组
     * @param parentId
     * @return
     */
    public List<Group> getChildrenGroup(String parentId) {
        StringRequest request = new StringRequest();
        request.setReqData(parentId);
        initRequest(request);

        GroupListResponse response = groupFacade.queryChildrenGroup(request);
        validResponse(response);
        return response.getData();
    }
}
