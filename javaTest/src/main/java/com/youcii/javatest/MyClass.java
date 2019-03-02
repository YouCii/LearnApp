package com.youcii.javatest;

import com.youcii.javatest.tree.BinaryTreeNode;
import com.youcii.javatest.tree.SearchTreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdw on 2018/12/20.
 * <p>
 * 用于LeetCode的测试
 */
public class MyClass {

    /**
     * 单独打印"\n"是为了防止算法方法在回调String之前在内部打印了日志
     */
    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("无重复字符的最长子串的长度: " + lengthOfLongestSubstring("pwwkew"));

        System.out.println("\n");
        LinkedNode root1 = new LinkedNode(2);
        root1.next = new LinkedNode(4);
        root1.next.next = new LinkedNode(3);
        LinkedNode root2 = new LinkedNode(5);
        root2.next = new LinkedNode(6);
        root2.next.next = new LinkedNode(4);
        System.out.println("两数之和: " + addTwoNumbers(root1, root2));

        System.out.println("\n");
        BinaryTreeNode<Integer> node = rebuildTree(new Integer[]{1, 2, 4, 5, 7, 8, 3, 6}, new Integer[]{4, 2, 7, 5, 8, 1, 3, 6});
        System.out.println(normalTreeOperation(node));

        System.out.println("\n");
        System.out.println(searchTreeOperation());
    }

    /**
     * 无重复字符的最长子串的长度
     * 思路: 滑动窗口, 以begin/end为两端进行检测, 当end字符在窗口中已存在时, 把begin跳转到前者+1的位置, end继续向后, 直到end到达string最后一位.
     * 在此过程中, 记录end每次位移后的窗口长度中的最大值, 循环完成时, 此值就是无重复字符的最长子串的长度
     */
    private static int lengthOfLongestSubstring(String string) {
        int maxLength = 0, length = string.length();
        Map<Character, Integer> map = new HashMap<>(length);
        for (int begin = 0, end = 0; end < length; end++) {
            char endChar = string.charAt(end);
            if (map.containsKey(endChar)) {
                // begin = map.get(endChar) + 1; // 这种方式有可能会导致begin回移
                begin = Math.max(begin, map.get(endChar) + 1); // 把尾部移到重复字符前者的后一位, 从这里继续
            }
            // 每次都要计算, 而不是在!map.containsKey(endChar)时才执行,
            // 避免出现在begin前存在重复字符进入了map.containsKey(endChar)分支导致当前字符统计不到的问题
            maxLength = Math.max(maxLength, end - begin + 1);
            map.put(endChar, end);
        }
        return maxLength;
    }

    /**
     * 两数之和, 要求:
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
    private static String addTwoNumbers(LinkedNode link1, LinkedNode link2) {
        // 可以在链表根节点放置一个0, 简化很多非空判断
        // ListNode rootNode = new ListNode(0), currentNode = new ListNode(0);

        // 进一步简化: 此时 currentNode 和 rootNode 本是一个, 后面又可以节省一步rootNode.next=currentNode
        LinkedNode rootNode = new LinkedNode(0), currentNode = rootNode;

        int carry = 0, singleNum;
        while (link1 != null || link2 != null) {
            singleNum = (link1 == null ? 0 : link1.val) + (link2 == null ? 0 : link2.val) + carry;
            carry = singleNum / 10;

            currentNode.next = new LinkedNode(singleNum % 10);
            currentNode = currentNode.next;

            link1 = link1 != null ? link1.next : null;
            link2 = link2 != null ? link2.next : null;
        }

        if (carry != 0) {
            currentNode.next = new LinkedNode(carry);
        }
        return rootNode.next.toString();
    }

    /**
     * 根据前序遍历和中序遍历重建二叉树
     *
     * 递归时要注意最后一层递归时的各种情况: 例如只有根节点, 根节点加一个子节点, 三个节点均有的情况
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> BinaryTreeNode<T> rebuildTree(T[] fore, T[] mid) {
        if (fore == null || mid == null || fore.length == 0 || mid.length == 0 || fore.length != mid.length) {
            return null;
        }
        T rootValue = fore[0];
        int rootIndex;
        BinaryTreeNode<T> root = null;
        // 前序的第一个点是根节点, 找到中序遍历的该点, 则左边的是左子树, 右边的是右子树;
        for (rootIndex = 0; rootIndex < mid.length; rootIndex++) {
            if (mid[rootIndex].equals(rootValue)) {
                root = new BinaryTreeNode<>(rootValue, null, null);
                break;
            }
        }
        // 说明中序遍历中没有相等的点
        if (root == null) {
            return null;
        }

        // 根据上方找到的根节点位置, 确定在前序/中序中的左右子树, 再采用递归来找到左右子树的根节点
        int nextLeftLength = rootIndex;
        if (nextLeftLength > 0) {
            T[] nextLeftFore = (T[]) new Comparable[nextLeftLength], nextLeftMid = (T[]) new Comparable[nextLeftLength];
            System.arraycopy(fore, 1, nextLeftFore, 0, nextLeftLength);
            System.arraycopy(mid, 0, nextLeftMid, 0, nextLeftLength);
            root.left = rebuildTree(nextLeftFore, nextLeftMid);
        }

        int nextRightLength = fore.length - nextLeftLength - 1;
        if (nextRightLength > 0) {
            T[] nextRightFore = (T[]) new Comparable[nextRightLength], nextRightMid = (T[]) new Comparable[nextRightLength];
            System.arraycopy(fore, rootIndex + 1, nextRightFore, 0, nextRightLength);
            System.arraycopy(mid, rootIndex + 1, nextRightMid, 0, nextRightLength);
            root.right = rebuildTree(nextRightFore, nextRightMid);
        }

        return root;
    }

    /**
     * 普通树遍历 :
     * 1.         1
     * 2.      2      3
     * 3.   4    5      6
     * 4.       7 8
     * <p>
     * 前序遍历：1  2  4  5  7  8  3  6
     * 中序遍历：4  2  7  5  8  1  3  6
     * 后序遍历：4  7  8  5  2  6  3  1
     * 层次遍历：1  2  3  4  5  6  7  8
     */
    private static String normalTreeOperation(BinaryTreeNode<Integer> root) {
        String result = "重建出的普通二叉树的各个操作: ";

        result += "\n前序递归: " + root.preOrderRecursive();
        result += "\n前序循环: " + root.preOrderCircle();
        result += "\n中序递归: " + root.inOrderRecursive();
        result += "\n中序循环: " + root.inOrderCircle();
        result += "\n后序递归: " + root.postOrderRecursive();
        result += "\n后序循环: " + root.postOrderCircle();
        result += "\n层级遍历: " + root.levelOrder();
        result += "\n最小值: " + root.findMin().val;
        result += "\n最大值: " + root.findMax().val;
        result += "\n容量: " + root.size();
        result += "\n高度: " + root.height();
        result += "\n是否包含: " + root.contains(5);
        result += "\n根据值查找节点: size=" + root.findNodeByVal(5).size();
        root.remove(5);
        result += "\n移除5之后的层级遍历: " + root.levelOrder();
        root.clear();
        result += "\n清除之后的层级遍历: " + root.levelOrder();

        return result;
    }

    /**
     * 搜索树遍历 :
     * 1.           5
     * 2.      1        6
     * 3.       2        7
     * 4.        3        8
     * 5.         4
     * <p>
     * 前序遍历：5  1  2  3  4  6  7  8
     * 中序遍历：1  2  3  4  5  6  7  8
     * 后序遍历：4  3  2  1  8  7  6  5
     * 层次遍历：5  1  6  2  7  3  8  4
     * <p>
     * 非平衡二叉树, 极端情况下会退化为O(logn)的链表
     */
    private static String searchTreeOperation() {
        String result = "搜索二叉树的各个操作: ";

        SearchTreeNode<Integer> root = new SearchTreeNode<>(5, null, null);
        for (int i = 1; i < 9; i++) {
            root.insert(i);
        }

        result += "\n前序递归: " + root.preOrderRecursive();
        result += "\n前序循环: " + root.preOrderCircle();
        result += "\n中序递归: " + root.inOrderRecursive();
        result += "\n中序循环: " + root.inOrderCircle();
        result += "\n后序递归: " + root.postOrderRecursive();
        result += "\n后序循环: " + root.postOrderCircle();
        result += "\n层级遍历: " + root.levelOrder();
        result += "\n最小值: " + root.findMin().val;
        result += "\n最大值: " + root.findMax().val;
        result += "\n容量: " + root.size();
        result += "\n高度: " + root.height();
        result += "\n是否包含: " + root.contains(5);
        result += "\n根据值查找节点: " + root.findNodeByVal(5);
        root.remove(5);
        result += "\n移除5之后的中序遍历: " + root.inOrderRecursive();
        root.clear();
        result += "\n清除之后的层级遍历: " + root.levelOrder();

        return result;
    }
}
