package com.youcii.javatest.tree

/**
 * Created by jdw on 2019/2/21.
 */
interface TreeNode<T : Comparable<T>> {

    /**
     * 插入(可能会有重复值)
     * @return true if sccess
     */
    fun insert(t: T): Boolean

    /**
     * 移除所有值为t的节点
     * @return true if success
     */
    fun remove(t: T): Boolean

    /**
     * 是否包含
     * @return true if contains
     */
    fun contains(t: T): Boolean

    /**
     * 根据值查找节点
     */
    fun findNodeByVal(t: T): List<TreeNode<T>>

    /**
     * 查找最小值
     */
    fun findMin(): T

    /**
     * 查找最大值
     */
    fun findMax(): T

    /**
     * 以此node为根节点的tree的节点总数目
     */
    fun size(): Int

    /**
     * 以此node为根节点的tree的节点深度
     */
    fun height(): Int

    /**
     * 清空所有子节点
     */
    fun clear()

    /**
     * 前序遍历, 递归方式
     * (DLR)
     */
    fun preOrderRecursive(): String

    /**
     * 中序遍历, 递归方式
     * (LDR)
     */
    fun inOrderRecursive(): String

    /**
     * 后序遍历, 递归方式
     * (LRD)
     */
    fun postOrderRecursive(): String

    /**
     * 层次遍历
     * (D, L R, LL LR RL RR )
     */
    fun levelOrder(): String
}