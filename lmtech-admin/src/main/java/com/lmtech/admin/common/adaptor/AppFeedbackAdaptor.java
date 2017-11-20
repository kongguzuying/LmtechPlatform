package com.lmtech.admin.common.adaptor;

import com.lmtech.common.PageData;
import com.lmtech.infrastructure.model.AppFeedback;

import java.util.List;

/**
 * App反馈服务适配器
 * Created by huang.jb on 2017-3-28.
 */
public class AppFeedbackAdaptor extends ServiceAdaptorBase implements ControllerManager<AppFeedback> {
    @Override
    public String add(AppFeedback appFeedback) {
        return null;
    }

    @Override
    public String edit(AppFeedback appFeedback) {
        return null;
    }

    @Override
    public void remove(String id) {

    }

    @Override
    public AppFeedback get(String id) {
        return null;
    }

    @Override
    public List<AppFeedback> getAll() {
        return null;
    }

    @Override
    public PageData getPageData(AppFeedback param, int pageIndex, int pageSize) {
        return null;
    }
}
