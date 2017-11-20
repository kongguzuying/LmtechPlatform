package com.lmtech.common;

/**
 * 树节点处理接口，用于TreeUtil中
 * @author huang.jb
 *
 */
public interface TreeItemHandler {
	void handle(TreeNode node, IComplex<?> complex);
}
