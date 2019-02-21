package com.youcii.javatest.tree;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jdw on 2019/2/20.
 * 普通二叉树
 */
public class BinaryTreeNode<T extends Comparable<T>> implements TreeNode<T> {
    public T val;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    public BinaryTreeNode(T val, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean insert(@NotNull T t) {
        return false;
    }

    @Override
    public boolean remove(@NotNull T t) {
        return false;
    }

    @Override
    public boolean contains(@NotNull T t) {
        return false;
    }

    @NotNull
    @Override
    public List<TreeNode<T>> findNodeByVal(@NotNull T t) {
        return null;
    }

    @NotNull
    @Override
    public T findMin() {
        return null;
    }

    @NotNull
    @Override
    public T findMax() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public void clear() {

    }

    /**
     * 前序遍历, 递归方式
     */
    @NotNull
    @Override
    public String preOrderRecursive() {
        StringBuilder builder = new StringBuilder();
        builder.append(val);
        if (left != null) {
            builder.append(left.preOrderRecursive());
        }
        if (right != null) {
            builder.append(right.preOrderRecursive());
        }
        return builder.toString();
    }

    /**
     * 中序遍历, 递归方式
     */
    @NotNull
    @Override
    public String inOrderRecursive() {
        StringBuilder builder = new StringBuilder();
        if (left != null) {
            builder.append(left.inOrderRecursive());
        }
        builder.append(val);
        if (right != null) {
            builder.append(right.inOrderRecursive());
        }
        return builder.toString();
    }

    /**
     * 后序遍历, 递归方式
     */
    @NotNull
    @Override
    public String postOrderRecursive() {
        StringBuilder builder = new StringBuilder();
        if (left != null) {
            builder.append(left.postOrderRecursive());
        }
        if (right != null) {
            builder.append(right.postOrderRecursive());
        }
        builder.append(val);
        return builder.toString();
    }

    /**
     * 层次遍历
     */
    @NotNull
    @Override
    public String levelOrder() {
        LinkedList<BinaryTreeNode> queue = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        queue.offer(this);
        while (queue.size() != 0) {
            BinaryTreeNode node = queue.poll();
            builder.append(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return builder.toString();
    }
}