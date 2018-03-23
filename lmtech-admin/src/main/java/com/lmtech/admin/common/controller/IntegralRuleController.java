package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.IntegralRule;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.IntegralRuleAdaptor;
import com.lmtech.common.ContextManager;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/integralRule")
public class IntegralRuleController extends ManagerControllerBase<IntegralRule>{

    @Autowired
    private IntegralRuleAdaptor integralRuleAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/integralRule";
    }

    @Override
    public ControllerManager<IntegralRule> getManager() {
        return integralRuleAdaptor;
    }

    @Override
    public void fillEntity(IntegralRule dbEntity, IntegralRule pageEntity) {
        dbEntity.setName(pageEntity.getName());
        dbEntity.setEndDate(pageEntity.getEndDate());
        dbEntity.setStartDate(pageEntity.getStartDate());
        dbEntity.setType(pageEntity.getType());
        dbEntity.setMerberType(pageEntity.getMerberType());
        dbEntity.setRemark(pageEntity.getRemark());
        dbEntity.setRule(pageEntity.getRule());
    }

    @RequestMapping(value = "/cancle.do")
    public RedirectView cancle(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
        RedirectView mav = new RedirectView();
        try {
            IntegralRule entity = this.getManager().get(id);
            entity.setStatus(IntegralRule.STATUS_THREE);
            entity.setUpdateDate(new Date());
            entity.setUpdateUser(ContextManager.getContext().getUserId());
            entity.setUpdateUserName(ContextManager.getContext().getUserName());
            this.getManager().edit(entity);
            mav.setUrl(String.format("/%1$s/list.do", this.getRequestMapping()));
        }  catch (Exception e) {
            LoggerManager.error(e);
            mav.setUrl("common/error");
        }
        return mav;
    }

    @RequestMapping(value = "/edit_rule.do", method = RequestMethod.GET)
    public ModelAndView edit_rule(HttpServletRequest request, HttpServletResponse response, @RequestParam String id,
                                  @RequestParam String status) {
        ModelAndView mav = new ModelAndView();
        if (!StringUtil.isNullOrEmpty(id)) {
            IntegralRule entity = this.getManager().get(id);
            mav.addObject("entity", entity);
        }
        mav.addObject("status2",status);
        mav.setViewName(String.format("%1$s/edit", this.getRequestMapping()));
        return mav;
    }
}
