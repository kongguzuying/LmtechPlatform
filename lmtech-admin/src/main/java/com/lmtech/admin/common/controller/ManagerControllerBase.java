package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.constants.Constant;
import com.lmtech.common.Context;
import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.infrastructure.model.User;
import com.lmtech.model.ConfigEntityBase;
import com.lmtech.model.DbEntityBase;
import com.lmtech.model.EntityBase;
import com.lmtech.util.*;
import org.apache.xerces.impl.dv.dtd.ENTITYDatatypeValidator;
import org.springframework.web.bind.ServletRequestDataBinder;
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
 * 管理类列表基础控制器
 * Created by huang.jb on 2016-7-12.
 */
public abstract class ManagerControllerBase<T extends EntityBase> extends ControllerBase {
    public abstract ControllerManager<T> getManager();
    public abstract void fillEntity(T dbEntity, T pageEntity);
    
    @InitBinder
	protected void initBinder(ServletRequestDataBinder binder) {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

    @RequestMapping(value = "/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        if (this.isEnablePager()) {
            PageData pageData = this.getPageData();
            mav.addObject("pageData", pageData);
        } else {
            List<T> entitys = this.getListData(request);
            mav.addObject("entitys", entitys);
        }

        //设置参数
        String filterKey = "filter";
        Map<String, Object> filterParams = ServletUtil.getParametersStartingWith(request, filterKey);
        if (filterParams != null && filterParams.size() > 0) {
            for (String key : filterParams.keySet()) {
                mav.addObject((filterKey + key), filterParams.get(key));
            }
        }

        mav.setViewName(String.format("%1$s/list", this.getRequestMapping()));
        return mav;
    }

    @RequestMapping(value = "/edit.do", method = RequestMethod.GET)
    public ModelAndView editIndex(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        if (!StringUtil.isNullOrEmpty(id)) {
            T entity = this.getManager().get(id);
            mav.addObject("entity", entity);
        }
        mav.setViewName(String.format("%1$s/edit", this.getRequestMapping()));
        return mav;
    }

    @RequestMapping(value = "/edit.do", method = RequestMethod.POST)
    public ModelAndView editCommit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute T entity) {
        ModelAndView mav = new ModelAndView();
        try {
            this.beforeSaveEntity(request, response, entity);
            this.saveEntity(entity);
            this.afterSaveEntity(request, response, entity);
            mav.setViewName(String.format("redirect:/%1$s/list.do", this.getRequestMapping()));
        } catch (Exception e) {
            LoggerManager.error(e);
            mav.addObject(e);
            mav.setViewName("common/error");
        }
        return mav;
    }

    @RequestMapping(value = "/syncedit.do", method = RequestMethod.POST)
    public ModelAndView syncEditCommit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute T entity) {
        try {
            this.beforeSaveEntity(request, response, entity);
            this.saveEntity(entity);
            this.afterSaveEntity(request, response, entity);
            return returnSuccess(response);
        } catch (Exception e) {
            LoggerManager.error(e);
            return returnFailed(response, e.getMessage());
        }
    }

    protected void beforeSaveEntity(HttpServletRequest request, HttpServletResponse response, T entity) {

    }

    protected void afterSaveEntity(HttpServletRequest request, HttpServletResponse response, T entity) {

    }

    protected void saveEntity(T entity) {
        String identified = null;
        if (DbEntityBase.class.isAssignableFrom(entity.getClass())) {
            identified = ((DbEntityBase) entity).getId();
        } else if (ConfigEntityBase.class.isAssignableFrom(entity.getClass())) {
            identified = ((ConfigEntityBase) entity).getCode();
        }

        Context context = ContextManager.getContext();
        if (StringUtil.isNullOrEmpty(identified)) {
            //添加
            if (context != null) {
                entity.setCreateDate(new Date());
                entity.setCreateUser(context.getUserId());
                entity.setCreateUserName(context.getUserName());
                entity.setUpdateDate(new Date());
                entity.setUpdateUser(context.getUserId());
                entity.setUpdateUserName(context.getUserName());
            }
            this.getManager().add(entity);
        } else {
            //更新
            T dbEntity = this.getManager().get(identified);
            if (dbEntity != null) {
                this.fillEntity(dbEntity, entity);
                //编辑时递增数据版本
                dbEntity.increaseDataVersion();
                //设置上下文参数
                if (context != null) {
                    dbEntity.setUpdateDate(new Date());
                    dbEntity.setUpdateUser(context.getUserId());
                    dbEntity.setUpdateUserName(context.getUserName());
                }
                this.getManager().edit(dbEntity);
            } else {
                //添加
                if (context != null) {
                    entity.setCreateDate(new Date());
                    entity.setCreateUserName(context.getUserName());
                    entity.setCreateUser(context.getUserId());
                    entity.setUpdateDate(new Date());
                    entity.setUpdateUser(context.getUserId());
                    entity.setUpdateUserName(context.getUserName());
                }
                this.getManager().add(entity);
            }
        }
    }

    @RequestMapping(value = "/remove.do")
    public RedirectView removeCommit(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
        RedirectView mav = new RedirectView();
        try {
            this.getManager().remove(id);
            mav.setUrl(String.format("/%1$s/list.do", this.getRequestMapping()));
        }  catch (Exception e) {
            LoggerManager.error(e);
            mav.setUrl("common/error");
        }
        return mav;
    }

    @RequestMapping(value = "/syncremove.do")
    public ModelAndView syncRemoveCommit(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
        try {
            this.getManager().remove(id);
            return returnSuccess(response, "删除成功！");
        }  catch (Exception e) {
            LoggerManager.error(e);
            return returnFailed(response, e.getMessage());
        }
    }

    protected  boolean isEnablePager() {
        //默认启用分页
        return true;
    }

    /**
     * 获取列表数据
     * @return
     */
    protected  List<T> getListData(HttpServletRequest request) {
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromHttpRequest(request);
        Map<Object, Object> params = getParamsMap(propertyFilters);
        //加入逻辑删除控制
        params.put(" and isDelete=:isDelete|isDelete", false);
        return this.getManager().getAll();
    }

    /**
     * 获取分页数据
     * @return
     */
    protected PageData getPageData() {
        int pageIndex = this.getRequestPageIndex();
        T param = PropertyFilter.buildObjectFromHttpRequest(getRequest(), (Class<T>) ClassUtil.getGenericClass(getClass()));
        PageData pageData = this.getManager().getPageData(param, pageIndex, pageSize);

        return pageData;
    }

    /**
     * 获取分页数据
     * @return
     */
    protected PageData getPageData(HttpServletRequest request) {
        int pageIndex = this.getRequestPageIndex();
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromHttpRequest(getRequest());
        Map<Object, Object> params = getParamsMap(propertyFilters);

        //加入逻辑删除控制
        params.put(" and isDelete=:isDelete|isDelete", false);
        
        Map<String, Object> cpMap = this.getCustomParams(request); 
        if(null != cpMap && cpMap.size() > 0){
        	params.putAll(cpMap);
        }
        
        PageData pageData = this.getManager().getPageData(null, pageIndex, pageSize);

        return pageData;
    }
    
    /**
     * 获取自定义查询参数
     * 如：key =  and fullName like :fullName|fullName
     * val = %test%
     * @return
     */
    protected Map<String, Object> getCustomParams(HttpServletRequest request){
    	return null;
    }

    protected Map<Object, Object> getParamsMap(List<PropertyFilter> propertyFilters) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        if (!CollectionUtil.isNullOrEmpty(propertyFilters)) {
            for (PropertyFilter propertyFilter : propertyFilters) {
                StringBuilder key = new StringBuilder();
                key.append(" and ");
                key.append(propertyFilter.getPropertyName());
                key.append(" ");
                switch (propertyFilter.getMatchType()) {
                    case EQ:
                        key.append("=:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case NEQ:
                        key.append("!=:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case GE:
                        key.append(">=:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case GT:
                        key.append(">:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case IN:
                        key.append("in (:" + propertyFilter.getPropertyName() + ")");
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case NIN:
                        key.append("not in (:" + propertyFilter.getPropertyName() + ")");
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case LE:
                        key.append("<=:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    case LIKE:
                        key.append("like :" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), '%' + String.valueOf(propertyFilter.getMatchValue()) + '%');
                        break;
                    case LT:
                        key.append("<:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                    default:
                        key.append("=:" + propertyFilter.getPropertyName());
                        key.append("|" + propertyFilter.getPropertyName());
                        params.put(key.toString(), propertyFilter.getMatchValue());
                        break;
                }
            }
        }
        return params;
    }

    protected int getRequestPageIndex() {
        String strPageIndex = getRequest().getParameter("pageIndex");
        if (!StringUtil.isNullOrEmpty(strPageIndex)) {
            return Integer.parseInt(strPageIndex);
        } else {
            return 1;
        }
    }
    /**
     * 获取当前登录的用户
     * @param request
     * @return
     */
    protected User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
    	return user;
    }
}
