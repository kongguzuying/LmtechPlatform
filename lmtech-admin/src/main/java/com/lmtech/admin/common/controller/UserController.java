package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.AccountAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.TenancyAdaptor;
import com.lmtech.admin.common.adaptor.UserAdaptor;
import com.lmtech.admin.common.vo.UserModel;
import com.lmtech.common.ContextManager;
import com.lmtech.infrastructure.model.Tenancy;
import com.lmtech.infrastructure.model.User;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理控制器
 * Created by huang.jb on 2016-7-14.
 */
@Controller
@RequestMapping("/platform/user")
public class UserController extends ManagerControllerBase<User> {
	@Autowired
	private UserAdaptor userAdaptor;
	@Autowired
	private AccountAdaptor accountAdaptor;
	@Autowired
	private TenancyAdaptor tenancyAdaptor;

	@Override
	public ControllerManager<User> getManager() {
		return userAdaptor;
	}

	@Override
	protected void beforeSaveEntity(HttpServletRequest request, HttpServletResponse response, User entity) {
		super.beforeSaveEntity(request, response, entity);

		ContextManager.setValue("userLoginName", request.getParameter("loginName"));
	}

	@Override
    public void fillEntity(User dbEntity, User pageEntity) {
        dbEntity.setNickName(pageEntity.getNickName());
        dbEntity.setRealName(pageEntity.getRealName());
        dbEntity.setTenancyId(pageEntity.getTenancyId());
        dbEntity.setEmail(pageEntity.getEmail());
        dbEntity.setMobile(pageEntity.getMobile());
        dbEntity.setQq(pageEntity.getQq());
    }

	@Override
	public ModelAndView editIndex(HttpServletRequest request, HttpServletResponse response, String id) {
		ModelAndView mv = super.editIndex(request, response, id);

		List<Tenancy> tenancyList = tenancyAdaptor.getAll();
		Tenancy empty = new Tenancy();
		empty.setId("");
		empty.setName("无租户");
		tenancyList.add(0, empty);
		mv.addObject("tenancyList", tenancyList);
		mv.addObject("hideLoginName", !StringUtil.isNullOrEmpty(id));

		return mv;
	}

	@Override
    public String getRequestMapping() {
        return "platform/user";
    }
    
    /**
	 * 根据部门id查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getByDid.do")
	public ModelAndView ajaxByDid(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String did) {
		if(StringUtil.isNullOrEmpty(did)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		List<User> us = userAdaptor.getUserByGroupId(did);
		responseObjectJson(response, us);
		return null;
	}
	
	/**
	 * 根据id查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getById.do")
	public ModelAndView getById(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String id) {
		if(StringUtil.isNullOrEmpty(id)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		User user = userAdaptor.get(id);
		responseObjectJson(response, user);
		return null;
	}
	
	@RequestMapping(value = "/toEdit.do")
    public ModelAndView toEdit(HttpServletRequest request,
			HttpServletResponse response ,@RequestParam String id, 
			@RequestParam String groupId) {
		if(StringUtil.isNullOrEmpty(groupId)
				&& StringUtil.isNullOrEmpty(id)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
        ModelAndView mav = new ModelAndView();
        User entity;
        if(!StringUtil.isNullOrEmpty(id)){
        	entity = userAdaptor.get(id);
        }else{
        	entity = new User();
        }
        mav.addObject("entity", entity);
		mav.addObject("groupId", groupId);
        mav.setViewName(getRequestMapping() + "/edit");
        return mav;
    }
	
	/**
	 * 添加和修改
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/doEdit.do",method=RequestMethod.POST)
	public ModelAndView doEdit(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute UserModel model) {
		if(StringUtil.isNullOrEmpty(model.getName())) {
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		model.setName(model.getName().trim());

		User user = new User();
		user.setNickName(model.getName());
		user.setId(model.getId());
		user.setEmail(model.getEmail());
		user.setQq(model.getQq());
		user.setMobile(model.getMobile());
		if(StringUtil.isNullOrEmpty(model.getId())){
			//添加用户
			String id = userAdaptor.add(user);
			//添加分组关联
			userAdaptor.addGroupUser(model.getGroupId(), id);
			model.setId(id);
		}else{
			User dbUser = userAdaptor.get(model.getId());
			dbUser.setMobile(user.getMobile());
			dbUser.setQq(user.getQq());
			dbUser.setEmail(user.getEmail());
			dbUser.setNickName(user.getNickName());
			dbUser.increaseDataVersion();
			userAdaptor.edit(dbUser);
		}
		return returnSuccess(response, "用户添加成功", model);
	}
	
	@RequestMapping(value = "/disable.do",method=RequestMethod.POST)
	public ModelAndView disable(HttpServletRequest request,
			HttpServletResponse response, String id) {
		if(StringUtil.isNullOrEmpty(id)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		User user = userAdaptor.get(id);
		if (user != null) {
			user.setStatus(User.STATUS_DISABLE);
			userAdaptor.edit(user);
		}
		return returnSuccess(response, "设置用户状态成功", user);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getByKey.do",method=RequestMethod.POST)
	public ModelAndView getByKey(HttpServletRequest request,
			HttpServletResponse response, String key) {
		List<User> us = null;
		if(!StringUtil.isNullOrEmpty(key)){
			key = key.trim();
			us = userAdaptor.getUserByKey(key);
		}
		return returnSuccess(response, "", us);
	}
	
	/**
	 * 切换部门
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/moveDept.do",method=RequestMethod.POST)
	public ModelAndView moveDept(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String userId, @RequestParam String oldGroupId, @RequestParam String newGroupId) {
		if(StringUtil.isNullOrEmpty(userId)
				|| StringUtil.isNullOrEmpty(oldGroupId)
				|| StringUtil.isNullOrEmpty(newGroupId)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		userAdaptor.changeGroup(userId, oldGroupId, newGroupId);
		return returnSuccess(response, "用户切换分组成功");
	}
	
	@RequestMapping(value = "/resetPw.do",method=RequestMethod.POST)
	public ModelAndView resetPw(HttpServletRequest request,
			HttpServletResponse response, String id) {
		if(StringUtil.isNullOrEmpty(id)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		accountAdaptor.changePswd(id, "123456");
		return returnSuccess(response, "重置密码成功", id);
	}
	/**
	 * 设置密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/doSetPw.do",method=RequestMethod.POST)
	public ModelAndView doSetPw(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String userId, @RequestParam String pswd) {
		if(StringUtil.isNullOrEmpty(userId)
				|| StringUtil.isNullOrEmpty(pswd)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		accountAdaptor.changePswd(userId, pswd);
		return returnSuccess(response, "密码设置成功");
	}
	/**
	 * 设置密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSetPw.do")
	public ModelAndView toSetPw(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String id) {
		if(StringUtil.isNullOrEmpty(id)){
			return this.returnFailed(response, "参数错误", "参数错误");
		}
		ModelAndView mav = new ModelAndView(getRequestMapping() +"/setpw");
		mav.addObject("id", id);
		return mav;
	}
}
