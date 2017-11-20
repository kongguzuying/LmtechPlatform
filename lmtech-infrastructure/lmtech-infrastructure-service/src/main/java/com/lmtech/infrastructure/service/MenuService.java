package com.lmtech.infrastructure.service;

import java.util.List;

import com.lmtech.common.Tree;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.service.DbServiceBase;

/**
 * 菜单管理
 * @author huang.jb
 *
 */
public interface MenuService extends DbServiceBase<Menu> {
	/**
	 * 获取最顶层菜单
	 * @param userId
	 * @return
	 */
	List<Menu> getTopMenu(String userId);
	/**
	 * 获取某一用户的最顶层菜单
	 * @param userId
	 * @return
	 */
	List<Menu> getUserTopMenu(String userId);
	/**
	 * 获取子菜单
	 * @param parentId
	 * @param userId
	 * @return
	 */
	List<Menu> getSubMenu(String parentId, String userId);
	/**
	 * 获取所有校验过的菜单
	 * @return
	 */
	List<Menu> getAllValidMenu();
	/**
	 * 获取所有菜单树对象
	 * @return
	 */
	Tree getMenuTree();
	/**
	 * 获取所有菜单树对象
	 * @param parentId
	 * @param userId
	 * @return
	 */
	Tree getMenuTree(String parentId, String userId);
	/**
	 * 获取某一用户的菜单树对象
	 * @param userId
	 * @param parentId
	 * @return
	 */
	Tree getUserMenuTree(String userId, String parentId);
}
