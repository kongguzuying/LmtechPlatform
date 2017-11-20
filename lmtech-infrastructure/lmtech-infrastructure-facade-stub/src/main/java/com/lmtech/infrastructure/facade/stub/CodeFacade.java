package com.lmtech.infrastructure.facade.stub;

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
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 代码服务入口
 * Created by huang.jb on 2017-1-11.
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/code")
public interface CodeFacade {
    /**
     * 查询代码表
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryCodes", method = RequestMethod.POST)
    CodeTypeListResponse queryCodes(NormalRequest request);

    /**
     * 查询代码列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryCodesOfType", method = RequestMethod.POST)
    CodeItemListResponse queryCodesOfType(StringRequest request);

    /**
     * 获取代码类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCodeType", method = RequestMethod.POST)
    CodeTypeResponse getCodeType(StringRequest request);

    /**
     * 获取代码类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCodeTypeOfPage", method = RequestMethod.POST)
    PageDataResponse getCodeTypeOfPage(CodeTypePageRequest request);

    /**
     * 添加代码类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/addCodeType", method = RequestMethod.POST)
    StringResponse addCodeType(CodeTypeRequest request);

    /**
     * 更新代码类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/editCodeType", method = RequestMethod.POST)
    NormalResponse editCodeType(CodeTypeRequest request);

    /**
     * 删除代码类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeCodeType", method = RequestMethod.POST)
    NormalResponse removeCodeType(StringRequest request);

    /**
     * 获取代码类型下的代码项
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCodeItemOfType", method = RequestMethod.POST)
    CodeItemListResponse getCodeItemOfType(StringRequest request);

    /**
     * 获取代码项
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCodeItem", method = RequestMethod.POST)
    CodeItemResponse getCodeItem(StringRequest request);

    /**
     * 添加代码项
     * @param request
     * @return
     */
    @RequestMapping(value = "/addCodeItem", method = RequestMethod.POST)
    StringResponse addCodeItem(CodeItemRequest request);

    /**
     * 编辑代码项
     * @param request
     * @return
     */
    @RequestMapping(value = "/editCodeItem", method = RequestMethod.POST)
    NormalResponse editCodeItem(CodeItemRequest request);

    /**
     * 删除代码项
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeCodeItem", method = RequestMethod.POST)
    NormalResponse removeCodeItem(StringRequest request);
}
