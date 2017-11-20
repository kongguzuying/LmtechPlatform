package com.lmtech.admin.common.adaptor;

import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.MenuRequest;
import com.lmtech.infrastructure.facade.response.MenuListResponse;
import com.lmtech.infrastructure.facade.response.MenuResponse;
import com.lmtech.infrastructure.facade.stub.MenuFacade;
import com.lmtech.infrastructure.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单服务适配器
 * Created by huang.jb on 2017-3-29.
 */
@Service
public class MenuAdaptor extends ServiceAdaptorBase implements ControllerManager<Menu> {

    @Autowired
    private MenuFacade menuFacade;

    @Override
    public String add(Menu menu) {
        MenuRequest request = new MenuRequest();
        request.setReqData(menu);
        initRequest(request);

        StringResponse response = menuFacade.addMenu(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(Menu menu) {
        MenuRequest request = new MenuRequest();
        request.setReqData(menu);
        initRequest(request);

        StringResponse response = menuFacade.editMenu(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        NormalResponse response = menuFacade.removeMenu(request);
        validResponse(response);
    }

    @Override
    public Menu get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        MenuResponse response = menuFacade.getMenu(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<Menu> getAll() {
        NormalRequest request = new NormalRequest();
        initRequest(request);

        MenuListResponse response = menuFacade.getAllMenu(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public PageData getPageData(Menu param, int pageIndex, int pageSize) {
        return null;
    }

    /**
     * 获取当前用户菜单列表
     * @return
     */
    public List<Menu> getCurrentUserMenus() {
        String userId = ContextManager.getContext().getUserId();
        StringRequest request = new StringRequest(userId);
        initRequest(request);

        MenuListResponse response = menuFacade.queryUserMenus(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取所有校验过的菜单
     * @return
     */
    public List<Menu> getAllValidMenus() {
        NormalRequest request = new NormalRequest();
        initRequest(request);

        MenuListResponse response = menuFacade.queryAllValidMenus(request);
        validResponse(response);
        return response.getData();
    }
}
