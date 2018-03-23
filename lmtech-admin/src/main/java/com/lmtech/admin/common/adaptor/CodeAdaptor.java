package com.lmtech.admin.common.adaptor;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.PageDataResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.CodeItemRequest;
import com.lmtech.infrastructure.facade.request.CodeTypePageRequest;
import com.lmtech.infrastructure.facade.request.CodeTypeRequest;
import com.lmtech.infrastructure.facade.response.CodeItemListResponse;
import com.lmtech.infrastructure.facade.response.CodeItemResponse;
import com.lmtech.infrastructure.facade.response.CodeTypeListResponse;
import com.lmtech.infrastructure.facade.response.CodeTypeResponse;
import com.lmtech.infrastructure.facade.stub.CodeFacade;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代码表服务适配器
 * Created by huang.jb on 2017-3-28.
 */
@Service
public class CodeAdaptor extends ServiceAdaptorBase implements ControllerManager<CodeType> {

    @Autowired
    private CodeFacade codeFacade;

    @Override
    public String add(CodeType codeType) {
        CodeTypeRequest request = new CodeTypeRequest();
        request.setReqData(codeType);
        initRequest(request);

        StringResponse response = codeFacade.addCodeType(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(CodeType codeType) {
        CodeTypeRequest request = new CodeTypeRequest();
        request.setReqData(codeType);
        initRequest(request);

        NormalResponse response = codeFacade.editCodeType(request);
        validResponse(response);
        return response.getMessage();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);
        NormalResponse response = codeFacade.removeCodeType(request);
        validResponse(response);
    }

    @Override
    public CodeType get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        CodeTypeResponse response = codeFacade.getCodeType(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<CodeType> getAll() {
        return null;
    }

    @Override
    public PageData getPageData(CodeType param, int pageIndex, int pageSize) {
        CodeTypePageRequest request = new CodeTypePageRequest();

        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        PageDataResponse response = codeFacade.getCodeTypeOfPage(request);
        validResponse(response);
        return (PageData) response.getData();
    }

    /**
     * 获取代码表
     * @return
     */
    public List<CodeType> getCodes() {
        NormalRequest request = new NormalRequest();
        initRequest(request);

        CodeTypeListResponse response = codeFacade.queryCodes(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 根据类型获取代码项
     * @param typeCode
     * @return
     */
    public List<CodeItem> getCodeItemOfType(String typeCode) {
        StringRequest request = new StringRequest(typeCode);
        initRequest(request);

        CodeItemListResponse response = codeFacade.queryCodesOfType(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取代码项文本
     * @param typeCode
     * @param codeItemValue
     * @return
     */
    public String getCodeItemText(String typeCode, String codeItemValue) {
        List<CodeItem> codeItems = getCodeItemOfType(typeCode);
        if (!CollectionUtil.isNullOrEmpty(codeItems)) {
            for (CodeItem codeItem : codeItems) {
                if (!StringUtil.isNullOrEmpty(codeItem.getValue()) && codeItem.getValue().equals(codeItemValue)) {
                    return codeItem.getName();
                }
            }
        }
        return null;
    }

    /**
     * 获取代码项值
     * @param typeCode
     * @param codeItemName
     * @return
     */
    public String getCodeItemValue(String typeCode, String codeItemName) {
        return null;
    }

    public CodeItemResponse getCodeItem(String codeItemId){
        StringRequest request = new StringRequest(codeItemId);
        initRequest(request);
        return codeFacade.getCodeItem(request);
    }

    public NormalResponse editCodeItem(CodeItem entity){
        CodeItemRequest request = new CodeItemRequest();
        request.setReqData(entity);
        initRequest(request);
        return codeFacade.editCodeItem(request);
    }

    public StringResponse addCodeItem(CodeItem entity){
        CodeItemRequest request = new CodeItemRequest();
        request.setReqData(entity);
        initRequest(request);
        return codeFacade.addCodeItem(request);
    }

    public NormalResponse removeCodeItem(String codeItemId){
        StringRequest request = new StringRequest(codeItemId);
        initRequest(request);
        return codeFacade.removeCodeItem(request);
    }
}
