package com.youcii.javatest;

import java.util.LinkedList;

/**
 * 二叉搜索树, 实现 4种遍历, 删除, 插入 等方法;
 */
class BinarySearchTreeNode {
    private int val;
    private BinarySearchTreeNode left;
    private BinarySearchTreeNode right;

    BinarySearchTreeNode(int x, BinarySearchTreeNode left, BinarySearchTreeNode right) {
        if (left != null && right != null) {
            if (left.val > x || right.val < x) {
                System.out.print(x + "是不符合二叉搜索树规则的点;\n");
            }
        }

        val = x;
        this.left = left;
        this.right = right;
    }

    /**
     * 前序遍历树, 递归方式
     * (根左右)
     */
    String preOrderTraversingWithRecursive() {
        StringBuilder builder = new StringBuilder();
        builder.append(val);
        if (left != null) {
            builder.append(left.preOrderTraversingWithRecursive());
        }
        if (right != null) {
            builder.append(right.preOrderTraversingWithRecursive());
        }
        return builder.toString();
    }

    /**
     * 中序遍历树, 递归方式
     * (左根右)
     */
    String midOrderTraversingWithRecursive() {
        StringBuilder builder = new StringBuilder();
        if (left != null) {
            builder.append(left.midOrderTraversingWithRecursive());
        }
        builder.append(val);
        if (right != null) {
            builder.append(right.midOrderTraversingWithRecursive());
        }
        return builder.toString();
    }

    /**
     * 后序遍历树, 递归方式
     * (左右根)
     */
    String postOrderTraversingWithRecursive() {
        StringBuilder builder = new StringBuilder();
        if (left != null) {
            builder.append(left.postOrderTraversingWithRecursive());
        }
        if (right != null) {
            builder.append(right.postOrderTraversingWithRecursive());
        }
        builder.append(val);
        return builder.toString();
    }

    /**
     * 层次遍历树,
     * (根, 左 右, 左左 左右 右左 右右 )
     */
    String levelOrderTraversingWithRecursive() {
        LinkedList<BinarySearchTreeNode> queue = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        queue.offer(this);
        while(queue.size() != 0){
            BinarySearchTreeNode node = queue.poll();
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