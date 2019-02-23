package com.youcii.javatest;

import com.youcii.javatest.tree.BinaryTreeNode;

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
        System.out.println(traverseTree());
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
     * 树遍历 :
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
    private static String traverseTree() {
        String result = "普通二叉树的各个操作: ";

        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(1, null, null);
        for (int i = 2; i < 9; i++) {
            root.insert(i);
        }

        result += "\n前序递归: " + root.preOrderRecursive();
        result += "\n中序递归: " + root.inOrderRecursive();
        result += "\n后序递归: " + root.postOrderRecursive();
        result += "\n层级遍历: " + root.levelOrder();
        result += "\n最小值: " + root.findMin();
        result += "\n最大值: " + root.findMax();
        result += "\n容量: " + root.size();
        result += "\n高度: " + root.height();
        result += "\n是否包含: " + root.contains(5);
        result += "\n对应节点: size=" + root.findNodeByVal(5).size();
        root.remove(5);
        result += "\n移除5之后的层级遍历: " + root.levelOrder();
        root.clear();
        result += "\n清除之后的层级遍历: " + root.levelOrder();

        return result;
    }

}
