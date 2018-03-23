package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.CodeAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.common.ContextManager;
import com.lmtech.infrastructure.facade.response.CodeItemResponse;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang.jb on 2016-12-9.
 */
@Controller
@RequestMapping("/platform/code")
public class CodeController extends ManagerControllerBase<CodeType> {

    @Autowired
    private CodeAdaptor codeAdaptor;

    @RequestMapping(value = "/codeListScript.do", method = RequestMethod.GET)
    public ModelAndView codeListScript(HttpServletResponse response) {
        List<CodeType> codeTypes = codeAdaptor.getCodes();
        String script = "var codes=" + JsonUtil.toJson(codeTypes);
        responseText(response, script, "application/json");
        return null;
    }

    @Override
    public String getRequestMapping() {
        return "platform/code";
    }
    @Override
    public ControllerManager<CodeType> getManager() {
        return codeAdaptor;
    }

    @Override
    public void fillEntity(CodeType dbEntity, CodeType pageEntity) {
        dbEntity.setCode(pageEntity.getCode());
        dbEntity.setRemark(pageEntity.getRemark());
        dbEntity.setName(pageEntity.getName());
        dbEntity.setParentCode(pageEntity.getParentCode());
    }

    @Override
    protected void saveEntity(CodeType entity) {
        String identified = entity.getCode();

        if (StringUtil.isNullOrEmpty(identified)) {
            //添加
            this.getManager().add(entity);
        } else {
            //更新
            CodeType dbEntity = this.getManager().get(identified);
            if (dbEntity != null) {
                this.fillEntity(dbEntity, entity);
                //编辑时递增数据版本
                dbEntity.increaseDataVersion();
                this.getManager().edit(dbEntity);
            } else {
                //添加
                this.getManager().add(entity);
            }
        }
    }

    @RequestMapping(value = "/itemList.do", method = RequestMethod.GET)
    public ModelAndView list( @RequestParam String code) {
        ModelAndView mav = new ModelAndView();
        List<CodeItem> list = codeAdaptor.getCodeItemOfType(code);
        mav.addObject("pageData", list);
        mav.addObject("code", code);
        mav.setViewName(String.format("%1$s/itemList", this.getRequestMapping()));
        return mav;
    }

    @RequestMapping(value = "/getCodeItem.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCodeItem(@RequestParam String code) {
        Map<String,Object> map = new HashMap<String,Object>();
        List<CodeItem> list = codeAdaptor.getCodeItemOfType(code);
        map.put("codeItem",list);
        return map;
    }

    @RequestMapping(value = "/itemEdit.do", method = RequestMethod.GET)
    public ModelAndView editIndex(@RequestParam String id,@RequestParam String code) {
        ModelAndView mav = new ModelAndView();
        if (!StringUtil.isNullOrEmpty(id)) {
            CodeItemResponse codeItemResponse = codeAdaptor.getCodeItem(id);
            CodeItem entity = codeItemResponse.getData();
            mav.addObject("entity", entity);
        }
        mav.addObject("code2", code);
        mav.setViewName(String.format("%1$s/itemEdit", this.getRequestMapping()));
        return mav;
    }

    @RequestMapping(value = "/syncEditCommit.do", method = RequestMethod.POST)
    public ModelAndView syncEditCommit(HttpServletResponse response,@ModelAttribute CodeItem entity) {
        try {
            if(!StringUtil.isNullOrEmpty(entity.getId())){//编辑
                codeAdaptor.editCodeItem(entity);
            }else{//新增
                codeAdaptor.addCodeItem(entity);
            }
            return returnSuccess(response);
        } catch (Exception e) {
            LoggerManager.error(e);
            return returnFailed(response, e.getMessage());
        }
    }

    @RequestMapping(value = "/itemRemove.do", method = RequestMethod.GET)
    public RedirectView itemRemove(@RequestParam String id,@RequestParam String code) {
        RedirectView mav = new RedirectView();
        try {
            codeAdaptor.removeCodeItem(id);
            mav.setUrl(String.format("/%1$s/itemList.do?code="+code, this.getRequestMapping()));
        }  catch (Exception e) {
            LoggerManager.error(e);
            mav.setUrl("common/error");
        }
        return mav;
    }
}
