package com.youcii.javatest;

import com.youcii.javatest.structure.LinkedNode;
import com.youcii.javatest.structure.tree.BinaryTreeNode;
import com.youcii.javatest.structure.tree.SearchTreeNode;
import com.youcii.javatest.structure.tree.TreeNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

        System.out.println("\n");
        node = rebuildTree(new Integer[]{1, 2, 4, 5, 7, 8, 3, 6}, new Integer[]{4, 2, 7, 5, 8, 1, 3, 6});
        List<TreeNode<Integer>> nodes = node.findNodeByVal(7);
        BinaryTreeNode<Integer> target = null;
        if (nodes.size() > 0) {
            target = (BinaryTreeNode<Integer>) nodes.get(0);
        }
        System.out.println("查找中序遍历顺序的下一个节点: " + findNextFollowInOrder(target));

        System.out.println("\n");
        int[] array = new int[]{3, 2, 4, 0, 8, 7};
        quickSort(array, 0, 5);
        System.out.println("快速排序结果: " + Arrays.toString(array));

        System.out.println("\n");
        array = new int[]{7, 8, 9, 4, 5, 6, 7};
        System.out.println("二分法查找自增旋转数组中的最小值: " + findMin1(array, 0, 6) + ":" + findMin2(array));
    }

    /**
     * 二分法差找自增数组的某旋转数组中的最小值
     * 旋转数组: 数组内元素平移后的数组, 例如{456123}是{123456}的一个旋转数组
     * <p>
     * 注意: 如果array比较小, 可用递归; 反之则要用效率较高的循环
     */
    private static int findMin(int[] array, int low, int height) {
        if (array.length < 10) {
            return findMin1(array, low, height);
        } else {
            return findMin2(array);
        }
    }

    /**
     * 循环方式
     */
    private static int findMin2(int[] array) {
        if (array == null || array.length == 0) {
            throw new NullPointerException();
        }
        if (array.length == 1) {
            return array[0];
        }
        // 旋转了0个元素的情况
        if (array[0] < array[array.length - 1]) {
            return array[0];
        }
        int min = 0;
        int low = 0, height = array.length - 1, middle = (height + low) / 2;
        while (height - low > 1) {
            if (array[low] == array[height] && array[low] == array[middle]) {
                min = array[low];
                for (int i = low + 1; i <= height; i++) {
                    if (array[i] < min) {
                        min = array[i];
                    }
                }
                break;
            }
            if (array[middle] <= array[low] && array[middle] <= array[height]) {
                height = middle;
                min = array[middle];
            }
            if (array[middle] >= array[low] && array[middle] >= array[height]) {
                low = middle;
                min = array[height];
            }
            middle = (low + height) / 2;
        }
        return min;
    }

    /**
     * 递归方式
     */
    private static int findMin1(int[] array, int low, int height) {
        if (array == null || array.length == 0 || low < 0 || height >= array.length || low > height) {
            throw new NullPointerException();
        }
        if (array.length == 1) {
            return array[0];
        }
        // 旋转了0个元素的情况
        if (array[low] < array[height]) {
            return array[low];
        }
        // 终止循环的条件
        if (height - low == 1) {
            return array[low] > array[height] ? array[height] : array[low];
        }
        int middle = (height + low) / 2;
        // 如果中间值与左右两端值都相等时, 采用顺序遍历
        if (array[low] == array[middle] && array[low] == array[height]) {
            int min = array[low];
            for (int i = low + 1; i <= height; i++) {
                if (array[i] < min) {
                    min = array[i];
                }
            }
            return min;
        } else if (array[middle] >= array[low] && array[middle] >= array[height]) {
            return findMin1(array, middle, height);
        } else if (array[middle] <= array[low] && array[middle] <= array[height]) {
            return findMin1(array, low, middle);
        }
        return array[middle];
    }

    /**
     * 快速排序
     */
    private static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            // 将numbers数组进行一分为二
            int middle = partition(numbers, low, high);
            // 对前半段进行递归排序
            quickSort(numbers, low, middle - 1);
            // 对后半段进行递归排序
            quickSort(numbers, middle + 1, high);
        }
    }

    /**
     * 查找出关键值在numbers数组排序后所在位置, 两边分段
     * <p>
     * 如果选择首位作为关键值, 就要先移动尾端, 以替换首位;
     * 反之, 则需要移动首端, 以替换尾端;
     */
    private static int partition(int[] numbers, int low, int high) {
        if (numbers == null || numbers.length < 1 || low > high) {
            throw new NullPointerException();
        }

        // 数组的随机值作为关键值
        int i = Double.valueOf(Math.random() * (high - low)).intValue() + low;
        int temp = numbers[i];
        numbers[i] = numbers[low];
        numbers[low] = temp;

        while (low < high) {
            while (low < high && numbers[high] >= temp) {
                high--;
            }
            numbers[low] = numbers[high]; // 比关键值小的记录移到前端
            while (low < high && numbers[low] <= temp) {
                low++;
            }
            numbers[high] = numbers[low]; // 比关键值大的记录移到后端
        }
        numbers[low] = temp; // 关键值记录到重合位置
        return low; // 返回关键值的位置
    }

    /**
     * 查找树的中序遍历顺序的下一个节点
     * 1.           1
     * 2.      2        3
     * 3.   4    5        6
     * 4.       7 8
     */
    private static <T extends Comparable<T>> T findNextFollowInOrder(BinaryTreeNode<T> target) {
        if (target == null) {
            return null;
        }
        // 如果有右子节点, 下一个就是其右子节点的最左叶子节点
        if (target.right != null) {
            BinaryTreeNode<T> current = target.right;
            while (current.left != null) {
                current = current.left;
            }
            return current.val;
        }
        // 如果没有右节点, 又没有父节点, 那就是根节点了, 没有下一个
        if (target.parent == null) {
            return null;
        }
        // 如果是父节点的左子节点, 下一个就是其父节点
        if (target == target.parent.left) {
            return target.parent.val;
        }
        // 执行到这里说明该节点是父节点的右子节点, 那么其下一个节点就是其前辈节点中第一个是其父节点的左子节点的那一个
        BinaryTreeNode<T> current = target.parent;
        while (current.parent != null) {
            if (current == current.parent.left) {
                return current.parent.val;
            }
            current = current.parent;
        }
        return null;
    }

    /**
     * 根据前序遍历和中序遍历重建二叉树
     * <p>
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

            BinaryTreeNode<T> leftRoot = rebuildTree(nextLeftFore, nextLeftMid);
            leftRoot.parent = root;
            root.left = leftRoot;
        }

        int nextRightLength = fore.length - nextLeftLength - 1;
        if (nextRightLength > 0) {
            T[] nextRightFore = (T[]) new Comparable[nextRightLength], nextRightMid = (T[]) new Comparable[nextRightLength];
            System.arraycopy(fore, rootIndex + 1, nextRightFore, 0, nextRightLength);
            System.arraycopy(mid, rootIndex + 1, nextRightMid, 0, nextRightLength);

            BinaryTreeNode<T> rightRoot = rebuildTree(nextRightFore, nextRightMid);
            rightRoot.parent = root;
            root.right = rightRoot;
        }

        return root;
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
}
