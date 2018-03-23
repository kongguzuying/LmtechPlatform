package com.lmtech.infrastructure.facade;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.PageDataResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.CodeItemQueryRequest;
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
import com.lmtech.infrastructure.service.CodeManager;
import com.lmtech.infrastructure.service.CodeService;
import com.lmtech.util.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang.jb on 2017-3-9.
 */
@RestController
@RequestMapping(value = "/code")
@Api(description = "代码表API入口")
public class CodeFacadeImpl implements CodeFacade {

    @Autowired
    private CodeService codeService;
    @Autowired
    private CodeManager codeManager;

    @Override
    @RequestMapping(value = "/queryCodes", method = RequestMethod.POST)
    @ApiOperation(value = "查询代码表")
    public CodeTypeListResponse queryCodes(@RequestBody NormalRequest request) {
        List<CodeType> codeTypes = codeService.getCodes();

        CodeTypeListResponse response = new CodeTypeListResponse();
        response.setSuccess(true);
        response.setData(codeTypes);
        response.setMessage("查询代码表数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryCodesOfType", method = RequestMethod.POST)
    @ApiOperation(value = "查询代码列表")
    public CodeItemListResponse queryCodesOfType(@RequestBody StringRequest request) {
        String typeCode = request.getReqData();

        List<CodeItem> codeItems = codeService.getCodeItemOfType(typeCode);

        CodeItemListResponse response = new CodeItemListResponse();
        response.setData(codeItems);
        response.setSuccess(true);
        response.setMessage("查询代码表数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCodeType", method = RequestMethod.POST)
    @ApiOperation(value = "获取代码类型")
    public CodeTypeResponse getCodeType(@RequestBody StringRequest request) {
        String typeCode = request.getReqData();

        CodeType codeType = codeManager.get(typeCode);

        CodeTypeResponse response = new CodeTypeResponse();
        response.setSuccess(true);
        response.setData(codeType);
        response.setMessage("获取代码分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCodeTypeOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取代码类别列表")
    public PageDataResponse getCodeTypeOfPage(@RequestBody CodeTypePageRequest request) {
        PageData pageData = codeManager.getPageData(request.getReqData(), request.getPageIndex(), request.getPageSize());

        PageDataResponse response = new PageDataResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取代码分类分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addCodeType", method = RequestMethod.POST)
    @ApiOperation(value = "添加代码类型")
    public StringResponse addCodeType(@RequestBody CodeTypeRequest request) {
        CodeType codeType = request.getReqData();

        String id = codeManager.add(codeType);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setData(id);
        response.setMessage("添加代码分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editCodeType", method = RequestMethod.POST)
    @ApiOperation(value = "更新代码类型")
    public NormalResponse editCodeType(@RequestBody CodeTypeRequest request) {
        CodeType codeType = request.getReqData();
        Assert.notNull(codeType.getCode(), "传入代码分类Code不允许为空");

        codeManager.edit(codeType);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("编辑代码分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeCodeType", method = RequestMethod.POST)
    @ApiOperation(value = "删除代码类型")
    public NormalResponse removeCodeType(@RequestBody StringRequest request) {
        String typeCode = request.getReqData();

        codeManager.remove(typeCode);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除代码分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCodeItemOfType", method = RequestMethod.POST)
    @ApiOperation(value = "获取代码类型下的代码项")
    public CodeItemListResponse getCodeItemOfType(@RequestBody StringRequest request) {
        String typeCode = request.getReqData();

        List<CodeItem> codeItems = codeManager.getCodeItemOfType(typeCode);

        CodeItemListResponse response = new CodeItemListResponse();
        response.setSuccess(true);
        response.setData(codeItems);
        response.setMessage("获取代码分类下的代码项数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCodeItem", method = RequestMethod.POST)
    @ApiOperation(value = "获取代码项")
    public CodeItemResponse getCodeItem(@RequestBody StringRequest request) {
        String codeItemId = request.getReqData();

        CodeItem codeItem = codeManager.getCodeItem(codeItemId);

        CodeItemResponse response = new CodeItemResponse();
        response.setData(codeItem);
        response.setSuccess(true);
        response.setMessage("获取代码项数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCodeItemName", method = RequestMethod.POST)
    @ApiOperation(value = "获取代码项值")
    public StringResponse getCodeItemName(@RequestBody CodeItemQueryRequest request) {
        String typeCode = request.getReqData().getTypeCode();
        String itemCode = request.getReqData().getItemCode();

        List<CodeItem> codeItems = codeManager.getCodeItemOfType(typeCode);
        StringResponse response = new StringResponse();
        if (!CollectionUtil.isNullOrEmpty(codeItems)) {
            for (CodeItem codeItem : codeItems) {
                if (codeItem.getCode().equals(itemCode)) {
                    response.setData(codeItem.getName());
                    break;
                }
            }
        }
        response.setSuccess(true);
        return response;
    }

    @Override
    @RequestMapping(value = "/addCodeItem", method = RequestMethod.POST)
    @ApiOperation(value = "添加代码项")
    public StringResponse addCodeItem(@RequestBody CodeItemRequest request) {
        CodeItem codeItem = request.getReqData();

        String id = codeManager.addCodeItem(codeItem);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setData(id);
        response.setMessage("添加代码项成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editCodeItem", method = RequestMethod.POST)
    @ApiOperation(value = "编辑代码项")
    public NormalResponse editCodeItem(@RequestBody CodeItemRequest request) {
        CodeItem codeItem = request.getReqData();
        Assert.notNull(codeItem.getId(), "传入代码项编号不允许为空");

        codeManager.editCodeItem(codeItem);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("编辑代码项成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeCodeItem", method = RequestMethod.POST)
    @ApiOperation(value = "删除代码项")
    public NormalResponse removeCodeItem(@RequestBody StringRequest request) {
        String itemCode = request.getReqData();

        codeManager.removeCodeItem(itemCode);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除代码项成功");
        return response;
    }
}
