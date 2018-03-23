package com.lmtech.util;

import java.util.ArrayList;
import java.util.List;

import com.lmtech.common.IComplex;
import com.lmtech.common.TreeItemHandler;
import com.lmtech.common.Tree;
import com.lmtech.common.TreeNode;

/**
 * 树工具类
 * @author huang.jb
 *
 */
public class TreeUtil {
	/**
	 * 将嵌套类型转换为树
	 * @param treeId
	 * @param complexs
	 * @return
	 */
	public static Tree convertToTree(String treeId, List<IComplex<?>> complexs) {
		return convertToTree(treeId, complexs, null);
	}
	/**
	 * 将嵌套类型转换为树
	 * @param treeId
	 * @param complexs
	 * @param treeItemHandler
	 * @return
	 */
	public static Tree convertToTree(String treeId, List<IComplex<?>> complexs, TreeItemHandler treeItemHandler) {
		return convertToTree(treeId, treeId, treeId, complexs, treeItemHandler);
	}
	/**
	 * 将嵌套类型转换为树
	 * @param treeId
	 * @param treeName
	 * @param treeTitle
	 * @param complexs
	 * @param treeItemHandler
	 * @return
	 */
	public static Tree convertToTree(String treeId, String treeName, String treeTitle, List<IComplex<?>> complexs, TreeItemHandler treeItemHandler) {
		Tree tree = new Tree();
		tree.setId(treeId);
		tree.setName(treeName);
		tree.setTitle(treeTitle);
		if (complexs != null) {
			for (IComplex<?> item : complexs) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(item.getId());
				treeNode.setParentId(item.getParentId());
				treeNode.setText(item.getId());
				if (treeItemHandler != null) {
					treeItemHandler.handle(treeNode, item);
				}
				tree.addTreeNode(treeNode);
			}
			
			//填充父子关系
			int treeNodeLength = 0;
			do {
				treeNodeLength = tree.getTreeNodes().size();
				List<String> deleteItem = new ArrayList<String>();
				for (TreeNode item : tree.getTreeNodes().values()) {
					TreeNode treeNode = tree.queryTreeNode(item.getParentId());
					if (treeNode != null) {
						treeNode.addChildren(item);
						deleteItem.add(item.getId());
						
						//已添加子项的节点不能删除
						if (deleteItem.contains(treeNode.getId())) {
							deleteItem.remove(treeNode.getId());
						}
					}
				}
				for (String id : deleteItem) {
					tree.getTreeNodes().remove(id);
				}
			} while (treeNodeLength != tree.getTreeNodes().size());
		}
		return tree;
	}
}
