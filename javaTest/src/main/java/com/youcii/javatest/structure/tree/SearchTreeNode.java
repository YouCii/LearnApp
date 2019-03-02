package com.youcii.javatest.structure.tree;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdw on 2019/2/21.
 * <p>
 * 二叉搜索树
 */
public class SearchTreeNode<T extends Comparable<T>> extends BinaryTreeNode<T> {

    public SearchTreeNode(T val, SearchTreeNode<T> left, SearchTreeNode<T> right) {
        super(val, left, right);

        boolean wrongNode = false;

        if (left != null) {
            if (val.compareTo(left.val) < 0) {
                wrongNode = true;
            }
        }
        if (right != null) {
            if (val.compareTo(right.val) > 0) {
                wrongNode = true;
            }
        }

        if (wrongNode) {
            System.out.print(val + "是不符合二叉搜索树规则的点;\n");
        }
    }

    public SearchTreeNode(T val, BinaryTreeNode<T> parent) {
        this(val, null, null);
        this.parent = parent;
    }

    /**
     * 排序二叉树不允许重复值
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
        int compare = t.compareTo(val);
        switch (compare) {
            case 1:
                if (right == null) {
                    right = new SearchTreeNode<>(t, this);
                    return true;
                } else {
                    return right.insert(t);
                }
            case -1:
                if (left == null) {
                    left = new SearchTreeNode<>(t, this);
                    return true;
                } else {
                    return left.insert(t);
                }
            default:
                return false;
        }
    }

    /**
     * 思路是用该节点的前驱(左子树的最大值)或后继(右子树的最小值)代替该值, 移除该值, 删除原来的前驱/后继节点
     */
    @Override
    public boolean remove(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot remove null");
        }

        int compare = t.compareTo(val);
        switch (compare) {
            case 1:
                return right.remove(t);
            case -1:
                return left.remove(t);
            default:
                // 当前节点是待删除的点;
                // 第一种情况: 没有左右子树
                if (left == null && right == null) {
                    clear();
                    return true;
                }
                // 第二种情况: 共有左右两子树, 使用前驱/后继均可
                else if (left != null && right != null) {
                    // 找到前驱以及前驱的父节点
                    SearchTreeNode<T> predecessor = (SearchTreeNode<T>) left, predecessorParent = this;
                    while (predecessor.right != null) {
                        predecessorParent = predecessor;
                        predecessor = (SearchTreeNode<T>) predecessor.right;
                    }
                    // 前驱只会是父节点的右子节点, 而且前驱没有右子节点, 所以只有这一种重排序 <==重要规律
                    predecessorParent.right = predecessor.left;

                    val = predecessor.val;
                    return true;
                }
                // 第三种情况: 仅有右子树
                else if (left == null) {
                    val = right.val;
                    left = right.left;
                    right = right.right;
                    return true;
                }
                // 第三种情况: 仅有左子树
                else {
                    val = left.val;
                    right = left.right;
                    left = left.left;
                    return true;
                }
        }
    }

    /**
     * 获取前驱节点
     * (中序前驱节点, 在搜索二叉树中体现为左子树的最大值)
     */
    private SearchTreeNode<T> predecessor() {
        if (this.left == null) {
            throw new NullPointerException("没有前驱节点");
        } else {
            return (SearchTreeNode<T>) this.left.findMax();
        }
    }

    /**
     * 获取后继节点
     * (中序后继节点, 在搜索二叉树中体现为右子树的最小值)
     */
    private SearchTreeNode<T> successor() {
        if (this.right == null) {
            throw new NullPointerException("没有后继节点");
        } else {
            return (SearchTreeNode<T>) this.right.findMin();
        }
    }

    @Override
    public boolean contains(T t) {
        if (t == null) {
            return false;
        }
        int compare = t.compareTo(val);
        switch (compare) {
            case 1:
                if (right == null) {
                    return false;
                } else {
                    return right.contains(t);
                }
            case -1:
                if (left == null) {
                    return false;
                } else {
                    return left.contains(t);
                }
            default:
                return true;
        }
    }

    @NotNull
    @Override
    public List<TreeNode<T>> findNodeByVal(T t) {
        if (t == null) {
            return new ArrayList<>();
        }
        int compare = t.compareTo(val);
        switch (compare) {
            case 1:
                if (right == null) {
                    return new ArrayList<>();
                } else {
                    return right.findNodeByVal(t);
                }
            case -1:
                if (left == null) {
                    return new ArrayList<>();
                } else {
                    return left.findNodeByVal(t);
                }
            default:
                return new ArrayList<TreeNode<T>>() {{
                    add(SearchTreeNode.this);
                }};
        }
    }

    /**
     * 不使用效率较差的递归
     */
    @NotNull
    @Override
    public SearchTreeNode<T> findMin() {
        SearchTreeNode<T> result = this;
        while (result.left != null) {
            result = (SearchTreeNode<T>) result.left;
        }
        return result;
    }

    @NotNull
    @Override
    public SearchTreeNode<T> findMax() {
        SearchTreeNode<T> result = this;
        while (result.right != null) {
            result = (SearchTreeNode<T>) result.right;
        }
        return result;
    }
}
