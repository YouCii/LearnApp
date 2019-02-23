package com.youcii.javatest;

import com.youcii.javatest.tree.BinaryTreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdw on 2018/12/20.
 * <p>
 * ����LeetCode�Ĳ���
 */
public class MyClass {

    /**
     * ������ӡ"\n"��Ϊ�˷�ֹ�㷨�����ڻص�String֮ǰ���ڲ���ӡ����־
     */
    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("���ظ��ַ�����Ӵ��ĳ���: " + lengthOfLongestSubstring("pwwkew"));

        System.out.println("\n");
        LinkedNode root1 = new LinkedNode(2);
        root1.next = new LinkedNode(4);
        root1.next.next = new LinkedNode(3);
        LinkedNode root2 = new LinkedNode(5);
        root2.next = new LinkedNode(6);
        root2.next.next = new LinkedNode(4);
        System.out.println("����֮��: " + addTwoNumbers(root1, root2));

        System.out.println("\n");
        System.out.println(traverseTree());
    }

    /**
     * ���ظ��ַ�����Ӵ��ĳ���
     * ˼·: ��������, ��begin/endΪ���˽��м��, ��end�ַ��ڴ������Ѵ���ʱ, ��begin��ת��ǰ��+1��λ��, end�������, ֱ��end����string���һλ.
     * �ڴ˹�����, ��¼endÿ��λ�ƺ�Ĵ��ڳ����е����ֵ, ѭ�����ʱ, ��ֵ�������ظ��ַ�����Ӵ��ĳ���
     */
    private static int lengthOfLongestSubstring(String string) {
        int maxLength = 0, length = string.length();
        Map<Character, Integer> map = new HashMap<>(length);
        for (int begin = 0, end = 0; end < length; end++) {
            char endChar = string.charAt(end);
            if (map.containsKey(endChar)) {
                // begin = map.get(endChar) + 1; // ���ַ�ʽ�п��ܻᵼ��begin����
                begin = Math.max(begin, map.get(endChar) + 1); // ��β���Ƶ��ظ��ַ�ǰ�ߵĺ�һλ, ���������
            }
            // ÿ�ζ�Ҫ����, ��������!map.containsKey(endChar)ʱ��ִ��,
            // ���������beginǰ�����ظ��ַ�������map.containsKey(endChar)��֧���µ�ǰ�ַ�ͳ�Ʋ���������
            maxLength = Math.max(maxLength, end - begin + 1);
            map.put(endChar, end);
        }
        return maxLength;
    }

    /**
     * ����֮��, Ҫ��:
     * ���룺(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * �����7 -> 0 -> 8
     * ԭ��342 + 465 = 807
     */
    private static String addTwoNumbers(LinkedNode link1, LinkedNode link2) {
        // ������������ڵ����һ��0, �򻯺ܶ�ǿ��ж�
        // ListNode rootNode = new ListNode(0), currentNode = new ListNode(0);

        // ��һ����: ��ʱ currentNode �� rootNode ����һ��, �����ֿ��Խ�ʡһ��rootNode.next=currentNode
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
     * ������ :
     * 1.         1
     * 2.      2      3
     * 3.   4    5      6
     * 4.       7 8
     * <p>
     * ǰ�������1  2  4  5  7  8  3  6
     * ���������4  2  7  5  8  1  3  6
     * ���������4  7  8  5  2  6  3  1
     * ��α�����1  2  3  4  5  6  7  8
     */
    private static String traverseTree() {
        String result = "��ͨ�������ĸ�������: ";

        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(1, null, null);
        for (int i = 2; i < 9; i++) {
            root.insert(i);
        }

        result += "\nǰ��ݹ�: " + root.preOrderRecursive();
        result += "\n����ݹ�: " + root.inOrderRecursive();
        result += "\n����ݹ�: " + root.postOrderRecursive();
        result += "\n�㼶����: " + root.levelOrder();
        result += "\n��Сֵ: " + root.findMin();
        result += "\n���ֵ: " + root.findMax();
        result += "\n����: " + root.size();
        result += "\n�߶�: " + root.height();
        result += "\n�Ƿ����: " + root.contains(5);
        result += "\n��Ӧ�ڵ�: size=" + root.findNodeByVal(5).size();
        root.remove(5);
        result += "\n�Ƴ�5֮��Ĳ㼶����: " + root.levelOrder();
        root.clear();
        result += "\n���֮��Ĳ㼶����: " + root.levelOrder();

        return result;
    }

}
