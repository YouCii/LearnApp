package com.youcii.javatest.structure.tree;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by jdw on 2019/2/20.
 * 普通二叉树
 */
public class BinaryTreeNode<T extends Comparable<T>> implements TreeNode<T> {
    public T val;
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    // 为了"查找二叉树的下一个节点"题目创建, 遍历等操作均未利用此参数
    public BinaryTreeNode<T> parent;

    public BinaryTreeNode(T val, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        if (val == null) {
            throw new NullPointerException("Cannot insert null");
        }
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public BinaryTreeNode(T val, BinaryTreeNode<T> parent) {
        this(val, null, null);
        this.parent = parent;
    }

    /**
     * 按照层级方向, 第一个左右子节点不全或都没有的节点就是目标节点
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
        LinkedList<BinaryTreeNode<T>> queue = new LinkedList<>();
        queue.offer(this);
        while (queue.size() != 0) {
            BinaryTreeNode<T> node = queue.poll();
            assert node != null;
            if (node.left == null) {
                node.left = new BinaryTreeNode<>(t, node);
                return true;
            } else {
                queue.offer(node.left);
            }
            if (node.right == null) {
                node.right = new BinaryTreeNode<>(t, node);
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
     * 层次遍历, 使用先进先出的队列方式缓存
     */
    @NotNull
    @Override
    public String levelOrder() {
        Queue<BinaryTreeNode> queue = new LinkedList<>();
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

    /**
     * 非递归前序遍历
     * 使用后进先出的堆栈
     */
    @NotNull
    @Override
    public String preOrderCircle() {
        StringBuilder builder = new StringBuilder();

        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        BinaryTreeNode<T> current = this;

        while (current != null || stack.size() > 0) {
            while (current != null) {
                builder.append(current.val);
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            // 转向右子树处理
            current = current.right;
        }
        return builder.toString() + "++" + otherPreCircle();
    }

    /**
     * 非递归中序遍历
     */
    @NotNull
    @Override
    public String inOrderCircle() {
        StringBuilder builder = new StringBuilder();
        Stack<BinaryTreeNode<T>> stack = new Stack<>();

        BinaryTreeNode<T> current = this;
        while (current != null || stack.size() > 0) {
            // 找到最左叶子点
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // 输出最左叶子点
            current = stack.pop();
            builder.append(current.val);

            // 转向右子树, 下次循环时把他作为根节点, 类似于递归了
            current = current.right;
        }
        return builder.toString();
    }

    /**
     * 非递归后序遍历
     */
    @NotNull
    @Override
    public String postOrderCircle() {
        StringBuilder builder = new StringBuilder();
        Stack<BinaryTreeNode<T>> stack = new Stack<>();

        BinaryTreeNode<T> current = this, last = null; // 如果last是当前子树, 说明该子树遍历过了, 可以继续向上层遍历了
        while (current != null || stack.size() > 0) {
            // 找到最左叶子点
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // 找到最左叶子点, 倒是还不输出, 因为要考虑是否要先输出右子节点
            current = stack.peek();

            // 没有右子节点或者右子节点已经输出过了, 就输出当前节点
            if (current.right == null || current.right == last) {
                builder.append(current.val);
                stack.pop();
                last = current;
                current = null;
            } else {
                // 转向右子树, 下次循环时把他作为根节点, 类似于递归了
                current = current.right;
            }
        }
        return builder.toString();
    }

    /**
     * 另外一种前序循环, 使用后进先出的堆栈
     */
    private String otherPreCircle() {
        StringBuilder builder = new StringBuilder();

        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        stack.push(this);

        BinaryTreeNode<T> root;
        while (stack.size() > 0) {
            root = stack.pop();
            builder.append(root.val);   // 第一位直接打印

            if (root.right != null) {
                stack.push(root.right); // 第三位先放进去
            }
            if (root.left != null) {
                stack.push(root.left);  // 第二位后放
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return val.toString();
    }
}