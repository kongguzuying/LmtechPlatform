package com.lmtech.infrastructure.service.impl;

import com.lmtech.common.*;
import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.MenuDao;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.service.MenuService;
import com.lmtech.infrastructure.service.RoleService;
import com.lmtech.infrastructure.service.UserService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;
import com.lmtech.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends AbstractDbServiceBaseImpl<Menu> implements MenuService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private MenuDao menuDao;

	@Override
	public Dao<Menu> getDao() {
		return menuDao;
	}

	@Override
	public List<Menu> getTopMenu(String userId) {
		return this.getSubMenu(Menu.ROOT_KEY, userId);
	}

	@Override
	public List<Menu> getUserTopMenu(String userId) {
		//取顶级菜单
		List<Menu> permedMenus = this.getUserPermSubMenu(Menu.ROOT_KEY, userId);
		if (permedMenus != null && permedMenus.size() > 0) {
			for (Menu permedMenu : permedMenus) {
				permedMenu.setNavigator(permedMenu.getName());
				//添加二级子菜单
				List<Menu> subMenus2 = this.getSubMenuOfPerm(permedMenu.getId(), userId);
				permedMenu.setChildrens(subMenus2 != null ? subMenus2 : new ArrayList<Menu>());
				if (subMenus2 != null) {
					for (Menu menu : subMenus2) {
						menu.setNavigator(permedMenu.getName() + "@@" + menu.getName());
						//添加三级子菜单
						List<Menu> subMenus3 = this.getSubMenuOfPerm(menu.getId(), userId);
						if (!CollectionUtil.isNullOrEmpty(subMenus3)) {
							for (Menu subMenu3 : subMenus3) {
								subMenu3.setNavigator(permedMenu.getName() + "@@" + menu.getName() + "@@" + subMenu3.getName());
							}
							menu.setChildrens(subMenus3);
						} else {
							menu.setChildrens(new ArrayList<Menu>());
						}
					}
				}
			}
		}
		return permedMenus;
	}

	@Override
	public List<Menu> getSubMenu(String parentId, String userId) {
		return this.getSubMenuOfPerm(parentId, userId);
	}

	@Override
	public List<Menu> getAllValidMenu() {
		return menuDao.selectAllValidMenu();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tree getMenuTree() {
		List<Menu> webMenus = menuDao.selectEnableMenus();
		
		return TreeUtil.convertToTree(String.valueOf("webMenu"), this.convertToComplex(webMenus), new TreeItemHandler() {
			@Override
			public void handle(TreeNode node, IComplex<?> complex) {
				Menu menu = (Menu)complex;
				node.setText(menu.getName());
				node.setAttribute("href", menu.getHref());
			}
		});
	}

	@Override
	public Tree getMenuTree(String parentId, String userId) {
		List<Menu> sbuMenuItems = this.getSubMenu(parentId, userId);
		
		return TreeUtil.convertToTree(String.valueOf(parentId), this.convertToComplex(sbuMenuItems), new TreeItemHandler() {
			@Override
			public void handle(TreeNode node, IComplex<?> complex) {
				Menu menu = (Menu)complex;
				node.setText(menu.getName());
				node.setAttribute("href", menu.getHref());
			}
		});
	}

	@Override
	public Tree getUserMenuTree(String userId, String parentId) {
		List<Menu> subMenus = getSubMenu(parentId, userId);
		Map<String, Menu> permMenus = getAccountPermMenu(userId);
		List<Menu> permedSubMenus = getPermedMenus(subMenus, permMenus);

		handleMenuUrl(permedSubMenus);
		
		return TreeUtil.convertToTree(String.valueOf(parentId), this.convertToComplex(permedSubMenus), new TreeItemHandler() {
			@Override
			public void handle(TreeNode node, IComplex<?> complex) {
				Menu menu = (Menu)complex;
				node.setText(menu.getName());
				node.setAttribute("href", menu.getHref());
			}
		});
	}
	
	private List<Menu> getUserPermSubMenu(String menuId, String userId) {
		List<Menu> topMenus = this.getSubMenu(menuId, userId);
		Map<String, Menu> permMenus = this.getAccountPermMenu(userId);
		List<Menu> permedMenus = this.getPermedMenus(topMenus, permMenus);
		return permedMenus;
	}
	
	private List<Menu> getSubMenuOfPerm(String parentId, String userId) {
		List<Menu> subMenus = menuDao.selectSubEnableMenus(parentId);
		Map<String, Menu> permMenus = getAccountPermMenu(userId);
		return getPermedMenus(subMenus, permMenus);
	}

	private Map<String, Menu> getAccountPermMenu(String userId) {
		Map<String, Menu> result = new LinkedHashMap<String, Menu>();
		List<Role> roleInfos = userService.getUserRoles(userId);
		if (roleInfos != null && roleInfos.size() > 0) {
			for (Role roleInfo : roleInfos) {
				List<Menu> menuInfos = roleService.getRoleMenus(roleInfo.getId());
				if (menuInfos != null && menuInfos.size() > 0) {
					for (Menu Menu : menuInfos) {
						result.put(Menu.getId(), Menu);
					}
				}
			}
		}
		return result;
	}
	
	private List<IComplex<?>> convertToComplex(List<Menu> menuInfos) {
		List<IComplex<?>> complexs = new ArrayList<IComplex<?>>();
		if (menuInfos != null) {
			for (Menu item : menuInfos) {
				complexs.add(item);
			}
		}
		return complexs;
	}
	
	private List<Menu> getPermedMenus(List<Menu> menuInfos, Map<String, Menu> permMenus) {
		List<Menu> menus = new ArrayList<Menu>();
		if (menuInfos != null && menuInfos.size() > 0 && permMenus != null && permMenus.size() > 0) {
			for (Menu topMenu : menuInfos) {
				if (permMenus.containsKey(topMenu.getId())) {
					menus.add(topMenu);
				}
			}
		}
		return menus;
	}
	
	private void handleMenuUrl(List<Menu> webMenus) {
		for (Menu item : webMenus) {
			String href = item.getHref();
			String appUrlBase = ApplicationConfig.APP_URL_BASE;
			if (!StringUtil.isNullOrEmpty(href) &&
				!href.startsWith("http://") && 
				!href.startsWith("https://")) {
				//将相对路径处理成绝对路径
				if (href.startsWith("//")) {
					href = href.substring(1, href.length() - 1);
				}
				if (appUrlBase.endsWith("//")) {
					appUrlBase = appUrlBase.substring(0, appUrlBase.length() - 2);
				}
				item.setHref(appUrlBase + "//" + href);
			}
		}
	}

	// property
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
