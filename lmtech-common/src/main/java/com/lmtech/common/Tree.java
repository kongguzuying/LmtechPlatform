package com.lmtech.common;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树
 * @author huang.jb
 *
 */
public class Tree {
	private String id;
	private String name;
	private String title;
	private Map<String, TreeNode> treeNodes = new HashMap<String, TreeNode>();

	/**
	 * 获取复合的树结点
	 * @return
	 */
	public List<TreeNode> getComplexTreeNodes() {
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (TreeNode item : treeNodes.values()) {
			result.add(item);
		}
		return result;
	}
	
	/**
	 * 获取树结点，只搜索第一级
	 * @param nodeId
	 * @return
	 */
	public TreeNode getTreeNode(String nodeId) {
		for (TreeNode item : treeNodes.values()) {
			if (item.getId() == nodeId) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 查询树结点，包括搜索子结点
	 * @return
	 */
	public TreeNode queryTreeNode(String nodeId) {
		List<TreeNode> result = queryTreeNodes(nodeId);
		
		return (result.size() > 0 ? result.get(0) : null);
	}
	
	/**
	 * 查询树结点列表，包括搜索子结点
	 * @return
	 */
	public List<TreeNode> queryTreeNodes(String nodeId) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		
		for (TreeNode item : treeNodes.values()) {
			//搜索根结点
			if (item.getId().equals(nodeId))
				result.add(item);

			//搜索子结点
			TreeNode treeNode = (TreeNode)item.getChildren(nodeId);
			if (treeNode != null) 
				result.add(treeNode);
		}
		return result;
	}
	
	public void addTreeNode(TreeNode treeNode) {
		if (!treeNodes.containsKey(treeNode.getId())) {
			treeNodes.put(treeNode.getId(), treeNode);
		}
	}
	
	public void deleteTreeNode(String nodeId) {
		if (treeNodes.containsKey(nodeId)) {
			treeNodes.remove(nodeId);
		}
	}
	
	//general getter and setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Map<String, TreeNode> getTreeNodes() {
		return treeNodes;
	}
	public void setTreeNodes(Map<String, TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}
}
