package com.youcii.javatest.tree;

/**
 * Created by jdw on 2019/2/21.
 * <p>
 * 二叉搜索树
 */
public class SearchTreeNode<T extends Comparable<T>> extends BinaryTreeNode<T> {

    public SearchTreeNode(T val, SearchTreeNode<T> left, SearchTreeNode<T> right) {
        super(val, left, right);

        if (left != null && right != null) {
            if (val.compareTo(left.val) < 0 || val.compareTo(right.val) > 0) {
                System.out.print(val + "是不符合二叉搜索树规则的点;\n");
            }
        }
    }

}
