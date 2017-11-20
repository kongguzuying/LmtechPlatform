package com.lmtech.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树结点
 * @author huang.jb
 *
 */
public class TreeNode extends ComplexBase<TreeNode> implements IComplex<TreeNode> {
	private static final long serialVersionUID = 1L;
	private String text;
	private Map<String, String> attrs = new HashMap<String, String>();
	
	public List<TreeNode> getAllChildrens() {
		ArrayList<TreeNode> complexs = new ArrayList<TreeNode>();
		getLevalsItem(complexs, this);
		return complexs;
	}

	public TreeNode getChildren(String id) {
		return this.getChildren(this, id);
	}
	
	public TreeNode getChildren(TreeNode treeNode, String id) {
		for (TreeNode item : treeNode.getChildrens()) {
			if (item.getId().equals(id)) {
				return item;
			} else {
				return getChildren(item, id);
			}
		}
		return null;
	}

	public List<TreeNode> getChildrens(String parentId) {
		for (TreeNode item : getChildrens()) {
			if (item.getParentId().equals(parentId)) {
				return item.getChildrens();
			}
		}
		return null;
	}

	public String getAttribute(String attrName) {
		if (attrs.containsKey(attrName)) {
			return attrs.get(attrName);
		}
		return null;
	}
	
	public void setAttribute(String attrName, String attrValue) {
		attrs.put(attrName, attrValue);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}
	
	/**
	 * 获取多级的子项
	 * @param complexs
	 * @param complex
	 */
	private void getLevalsItem(ArrayList<TreeNode> complexs, IComplex<TreeNode> complex) {
		for (TreeNode item : complex.getChildrens()) {
			if (item.getChildrens() == null || !item.hasChildren()) {
				complexs.add(item);
			} else {
				getLevalsItem(complexs, item);
			}
		}
	}
}
