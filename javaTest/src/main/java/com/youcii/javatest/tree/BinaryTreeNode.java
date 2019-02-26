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
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    public BinaryTreeNode(T val, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        if (val == null){
            throw new NullPointerException("Cannot insert null");
        }
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * 按照层级方向, 第一个没有左右子节点的节点就是目标节点
     */
    @Override
    public boolean insert(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot insert null");
        }
        if (val == null) {
            val = t;
            return true;
        }
        LinkedList<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(this);
        while (queue.size() != 0) {
            BinaryTreeNode node = queue.poll();
            assert node != null;
            if (node.left == null) {
                node.left = new BinaryTreeNode<>(t, null, null);
                return true;
            } else {
                queue.offer(node.left);
            }
            if (node.right == null) {
                node.right = new BinaryTreeNode<>(t, null, null);
                return true;
            } else {
                queue.offer(node.right);
            }
        }
        return false;
    }

    /**
     * 使用层次遍历的方式获取最后一位
     */
    @NotNull
    private BinaryTreeNode findLast() {
        LinkedList<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(this);
        while (queue.size() != 0) {
            BinaryTreeNode node = queue.poll();
            assert node != null;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (queue.size() == 0) {
                return node;
            }
        }
        return this;
    }

    /**
     * 按层次遍历记录所有非t点, 按层次顺序重新构建
     */
    @Override
    public boolean remove(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot remove null");
        }
        LinkedList<BinaryTreeNode<T>> queue = new LinkedList<>();
        LinkedList<T> postNodes = new LinkedList<>();
        queue.offer(this);
        while (queue.size() != 0) {
            BinaryTreeNode<T> node = queue.poll();
            assert node != null;

            if (!node.val.equals(t)) {
                postNodes.add(node.val);
            }

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        clear();
        while (postNodes.size() != 0) {
            T v = postNodes.poll();
            assert v != null;
            if (val == null) {
                val = v;
            } else {
                insert(v);
            }
        }
        return true;
    }

    @Override
    public boolean contains(T t) {
        if (t == null) {
            return false;
        } else if (val.equals(t)) {
            return true;
        } else if (left != null && left.contains(t)) {
            return true;
        } else return right != null && right.contains(t);
    }

    @NotNull
    @Override
    public List<TreeNode<T>> findNodeByVal(T t) {
        List<TreeNode<T>> result = new LinkedList<>();
        if (t == null) {
            return result;
        }
        if (val.equals(t)) {
            result.add(this);
        }
        if (left != null) {
            result.addAll(left.findNodeByVal(t));
        }
        if (right != null) {
            result.addAll(right.findNodeByVal(t));
        }
        return result;
    }

    @NotNull
    @Override
    public BinaryTreeNode<T> findMin() {
        BinaryTreeNode<T> min = this;
        if (left != null) {
            BinaryTreeNode<T> minInLeft = left.findMin();
            min = min.val.compareTo(minInLeft.val) < 0 ? min : minInLeft;
        }
        if (right != null) {
            BinaryTreeNode<T> minInRight = right.findMin();
            min = min.val.compareTo(minInRight.val) < 0 ? min : minInRight;
        }
        return min;
    }

    @NotNull
    @Override
    public BinaryTreeNode<T> findMax() {
        BinaryTreeNode<T> max = this;
        if (left != null) {
            BinaryTreeNode<T> maxInLeft = left.findMax();
            max = max.val.compareTo(maxInLeft.val) > 0 ? max : maxInLeft;
        }
        if (right != null) {
            BinaryTreeNode<T> maxInRight = right.findMax();
            max = max.val.compareTo(maxInRight.val) > 0 ? max : maxInRight;
        }
        return max;
    }

    @Override
    public int size() {
        return levelOrder().length();
    }

    @Override
    public int height() {
        int l = 0, r = 0;
        if (left != null) {
            l = left.height();
        }
        if (right != null) {
            r = right.height();
        }
        return 1 + Math.max(l, r);
    }

    @Override
    public void clear() {
        this.left = this.right = null;
        val = null;
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
     * 层次遍历, 使用队列方式缓存
     */
    @NotNull
    @Override
    public String levelOrder() {
        LinkedList<BinaryTreeNode> queue = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        queue.offer(this);
        while (queue.size() != 0) {
            BinaryTreeNode node = queue.poll();
            assert node != null;
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