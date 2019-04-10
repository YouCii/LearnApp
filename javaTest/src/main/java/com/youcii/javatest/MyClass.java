package com.youcii.javatest;

import com.youcii.javatest.structure.LinkedNode;
import com.youcii.javatest.structure.tree.BinaryTreeNode;
import com.youcii.javatest.structure.tree.SearchTreeNode;
import com.youcii.javatest.structure.tree.TreeNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by jdw on 2018/12/20.
 * <p>
 * 算法相关
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
        BinaryTreeNode<Integer> target1 = null;
        if (nodes.size() > 0) {
            target1 = (BinaryTreeNode<Integer>) nodes.get(0);
        }
        System.out.println("查找中序遍历顺序的下一个节点: " + findNextFollowInOrder(target1));

        System.out.println("\n");
        int[] array = new int[]{3, 2, 4, 0, 8, 7};
        quickSort(array, 0, 5);
        System.out.println("快速排序结果: " + Arrays.toString(array));

        System.out.println("\n");
        array = new int[]{7, 8, 9, 4, 5, 6, 7};
        System.out.println("二分法查找自增旋转数组中的最小值: " + findMin1(array, 0, 6) + ", " + findMin2(array));

        System.out.println("\n");
        char[][] data = {
                {'a', 'b', 't', 'g'},
                {'c', 'f', 'c', 's'},
                {'j', 'd', 'e', 'h'}};
        char[] target2 = {'a', 'c', 'j', 'd', 'e', 'h', 's', 'g', 't', 'b', 'f', 'c'};
        System.out.println("回溯法判断矩阵中是否存在某路径: " + containsPath(data, target2));

        System.out.println("\n");
        System.out.println("机器人的运动范围: " + moveNum(10, 100, 100));

        System.out.println("\n");
        System.out.println("剪绳子: 动态规划算法" + cutRopeByDynamicProgramming(8) + ", 贪婪算法: " + cutRopeByGreedy(8));

        System.out.println("\n");
        dataType();

        System.out.println("\n");
        print1ToMaxNumberByFullArrange(3);

        System.out.println("\n");
        LinkedNode root = new LinkedNode(1);
        root.next = new LinkedNode(2);
        root.next.next = new LinkedNode(3);
        root.next.next.next = new LinkedNode(4);
        root.next.next.next.next = new LinkedNode(5);
        root.next.next.next.next.next = new LinkedNode(6);
        root.next.next.next.next.next.next = new LinkedNode(7);
        System.out.println("删除重复的排序链表节点: " + deleteDuplicate(root));

        System.out.println("\n");
        System.out.println("正则表达式匹配: " + patternMatch("abcdef", ".*.*.*.*"));

        System.out.println("\n");
        char[] chars = {'+', '2'};
        System.out.println("校验一个String是否是数字: " + isNumber(chars));

        System.out.println("\n");
        LinkedNode root4 = new LinkedNode(1);
        root4.next = new LinkedNode(2);
        root4.next.next = new LinkedNode(3);
        root4.next.next.next = new LinkedNode(4);
        root4.next.next.next.next = new LinkedNode(5);
        root4.next.next.next.next.next = new LinkedNode(6);
        root4.next.next.next.next.next.next = new LinkedNode(7);
        root4.next.next.next.next.next.next.next = root4.next.next.next;
        System.out.println("链表中环的入口节点: " + getRingEnter(root4));

        System.out.println("\n");
        LinkedNode root5 = new LinkedNode(1);
        root5.next = new LinkedNode(2);
        root5.next.next = new LinkedNode(3);
        root5.next.next.next = new LinkedNode(4);
        root5.next.next.next.next = new LinkedNode(5);
        root5.next.next.next.next.next = new LinkedNode(6);
        root5.next.next.next.next.next.next = new LinkedNode(7);
        root5.next.next.next.next.next.next.next = new LinkedNode(8);
        System.out.println("反转链表: " + reverseLinked(root5));

        System.out.println("\n");
        BinaryTreeNode parent = rebuildTree(new Integer[]{1, 2, 4, 5, 7, 8, 3, 6}, new Integer[]{4, 2, 7, 5, 8, 1, 3, 6});
        BinaryTreeNode child = rebuildTree(new Integer[]{6, 7, 8}, new Integer[]{7, 6, 8});
        System.out.println("树的子结构: " + isSubTree2(parent, child));

        System.out.println("\n");
        int[] target = new int[]{1, 5, 2, 4, 8, 7, 3, 6};
        mergerSort(target, 0, target.length - 1);
        System.out.println("归并排序: " + Arrays.toString(target));

        System.out.println("\n");
        int[] pushArray = new int[]{1, 2, 3, 4, 5};
        int[] popArray = new int[]{4, 5, 2, 3, 1};
        System.out.println("两数组是否互为进出栈关系: " + isPopArray(pushArray, popArray));
    }

    /**
     * 注意, 入栈不一定是一下全部压入, 可能边压入边弹出
     *
     * @param pushArray 按入栈顺序存储的数组
     * @param popArray  按出栈顺序存储的数组
     * @return 返回顺序是否匹配
     */
    private static boolean isPopArray(int[] pushArray, int[] popArray) {
        if (pushArray == null || popArray == null) {
            throw new NullPointerException();
        }
        Stack<Integer> cache = new Stack<>();
        int indexPush = 0, indexPop = 0;
        while (indexPush < pushArray.length) {
            cache.push(pushArray[indexPush++]);
            while (indexPop < popArray.length && cache.peek() == popArray[indexPop]) {
                cache.pop();
                indexPop++;
            }
        }
        return cache.empty();
    }

    /**
     * 归并排序, 思路是分治法, 递归执行比较简单的合并已排序数组操作即可
     */
    private static void mergerSort(int[] array, int low, int high) {
        if (array == null || array.length < 2 || low < 0 || high > array.length - 1 || low >= high) {
            return;
        }
        int middle = (high + low) >>> 1;
        mergerSort(array, low, middle);
        mergerSort(array, middle + 1, high);

        mergeSortArrays(array, low, middle, high);
    }

    private static void mergeSortArrays(int[] array, int low, int middle, int high) {
        if (middle < low || middle > high) {
            return;
        }
        int[] cache = new int[high - low + 1];
        int indexLeft = low, indexRight = middle + 1, indexCache = 0;
        while (indexLeft <= middle && indexRight <= high) {
            if (array[indexLeft] < array[indexRight]) {
                cache[indexCache++] = array[indexLeft++];
            } else {
                cache[indexCache++] = array[indexRight++];
            }
        }
        while (indexLeft <= middle) {
            cache[indexCache++] = array[indexLeft++];
        }
        while (indexRight <= high) {
            cache[indexCache++] = array[indexRight++];
        }
        while (indexCache > 0) {
            array[high--] = cache[--indexCache];
        }
    }

    /**
     * 树的子结构: 层次遍历
     */
    private static boolean isSubTree1(BinaryTreeNode parent, BinaryTreeNode child) {
        if (parent == null || child == null) {
            return false;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        BinaryTreeNode current;
        queue.offer(parent);
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.val.equals(child.val) && startWithTree(current, child)) {
                return true;
            }

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return false;
    }

    /**
     * 树的子结构: 前序遍历
     */
    private static boolean isSubTree2(BinaryTreeNode parent, BinaryTreeNode child) {
        if (parent == null || child == null) {
            return false;
        }
        if (parent.val == child.val && startWithTree(parent, child)) {
            return true;
        } else {
            return isSubTree2(parent.left, child) || isSubTree2(parent.right, child);
        }
    }

    private static boolean startWithTree(BinaryTreeNode parent, BinaryTreeNode child) {
        if (child == null) {
            return true;
        }
        if (parent == null) {
            return false;
        }
        if (parent.val.equals(child.val)) {
            return startWithTree(parent.left, child.left) && startWithTree(parent.right, parent.right);
        } else {
            return false;
        }
    }

    /**
     * 合并两个递增链表, 保持递增
     */
    private static LinkedNode mergeLinked(LinkedNode node1, LinkedNode node2) {
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }

        LinkedNode header = null, current = null, min = null;
        while (node1 != null || node2 != null) {
            min = minNode(node1, node2);

            if (min == node1) {
                node1 = node1.next;
            } else {
                node2 = node2.next;
            }

            if (current == null) {
                header = current = min;
            } else {
                current.next = min;
                current = current.next;
            }
        }
        return header;
    }

    private static LinkedNode minNode(LinkedNode node1, LinkedNode node2) {
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }
        if (node1.val > node2.val) {
            return node2;
        }
        return node1;
    }

    /**
     * 反转链表
     */
    private static LinkedNode reverseLinked(LinkedNode header) {
        LinkedNode pre = null, current = header, next = null;
        while (current != null) {
            next = current.next;
            current.next = pre;

            pre = current;
            current = next;
        }
        return pre;
    }

    /**
     * 链表中环的入口节点
     * <p>
     * 1. 两个指针遍历, 速度2的指针与速度1的指针相遇说明有环
     * 2. 记录相遇位置, 再次回到此位置时, 移动距离为环的节点数目
     * 3. 两个指针遍历, 指针1首先移动环节点数步, 一起移动的首次相遇点即入口点
     */
    private static LinkedNode getRingEnter(LinkedNode header) {
        if (header == null) {
            return null;
        }
        LinkedNode slow = header, fast = header, meet = null;
        int ringChild = 0;
        while (fast != null && slow != null) {
            if (meet == null) {
                slow = slow.next;
                fast = fast.next;
                if (fast != null) {
                    fast = fast.next;
                    if (fast == slow) {
                        meet = fast;
                    }
                }
            } else {
                slow = slow.next;
                ringChild++;
                if (slow == meet) {
                    break;
                }
            }
        }
        if (slow == null || fast == null) {
            return null;
        }

        slow = fast = header;
        while (ringChild > 0) {
            fast = fast.next;
            ringChild--;
        }
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 校验一个String是否是数字
     * 正例: +2  -2  .2  2.  2E2  2e2  2E+2  2E-2  2.E-2
     * 反例: E2  2E  EE  .2. +-2  2e2.2
     * <p>
     * 思路一: 以小数点和E/e把字符串分为三部分, 第一部分可以为+/-(数字).(数字), 第二部分为纯数字, 第三部分为+/-(数字)
     * 思路二: O(n)遍历校验每个字符, 每个字符都有自己的规则, 此思路比较清晰, 以此实现
     */
    private static boolean isNumber(char[] chars) {
        if (chars == null || chars.length < 1) {
            return false;
        }
        boolean hasDot = false, hasE = false;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '+' || chars[i] == '-') {
                // +/-只会出现在第一位或者E的后一位
                if (i != 0 && chars[i - 1] != 'E' && chars[i - 1] != 'e') {
                    return false;
                }
            } else if (chars[i] == 'E' || chars[i] == 'e') {
                // E不允许出现在第一位/最后一位, 并且只能出现一次
                if (i == 0 || i == chars.length - 1 || hasE) {
                    return false;
                } else {
                    hasE = true;
                }
            } else if (chars[i] == '.') {
                // .不允许出现在E后面, 并且只能出现一次
                if (hasE || hasDot) {
                    return false;
                } else {
                    hasDot = true;
                }
            } else if (chars[i] < '0' || chars[i] > '9') {
                return false;
            }
        }
        return true;
    }


    /**
     * 正则表达式匹配
     * .可以表示任意字符, *表示前面的字符可以出现任意次数(包括0次)
     * 测试用例: s -- abcdef, p -- .*, ab.c*def, abzdef, abz*cdef, abcdef, ......, .*.*.*.*
     */
    private static boolean patternMatch(String s, String pattern) {
        if (s == null || pattern == null) {
            return false;
        }
        if (pattern.startsWith("*") || pattern.contains("**")) {
            throw new IllegalArgumentException("pattern格式错误");
        }
        return match(s, pattern);
    }

    private static boolean match(String s, String pattern) {
        if (pattern.startsWith("*")) {
            throw new IllegalArgumentException("pattern格式错误");
        }
        if (s.length() == 0) {
            return pattern.length() == 0;
        }
        if (pattern.length() == 0) {
            return false;
        }
        char cOfS = s.charAt(0), cOfP = pattern.charAt(0);
        if (pattern.length() == 1 || pattern.charAt(1) != '*') {
            if (cOfS == cOfP || cOfP == '.') {
                return match(s.substring(1), pattern.substring(1));
            } else {
                return false;
            }
        } else {
            if (cOfS == cOfP || cOfP == '.') {
                return match(s.substring(1), pattern) || match(s.substring(1), pattern.substring(2));
            } else {
                return match(s, pattern.substring(2));
            }
        }
    }

    /**
     * 删除重复的排序链表节点
     */
    private static LinkedNode deleteDuplicate(LinkedNode head) {
        if (head == null) {
            return null;
        }
        LinkedNode pre = null, current = head, next;
        while (current.next != null) {
            next = current.next;
            if (next.val == current.val) {
                while (next.next != null) {
                    if (next.next.val == next.val) {
                        next = next.next;
                    } else {
                        break;
                    }
                }
                if (pre == null) {
                    if (next.next == null) {
                        return null;
                    } else {
                        head.val = next.next.val;
                        head.next = next.next.next;
                    }
                } else {
                    if (next.next == null) {
                        pre.next = null;
                        return head;
                    } else {
                        current = next.next;
                        pre.next = next.next;
                    }
                }
            } else {
                pre = current;
                current = current.next;
            }
        }
        return head;
    }

    /**
     * 打印1, 2, 3, ... , n位最大数
     * byte数组, 各位的0~9全排列方式
     */
    private static void print1ToMaxNumberByFullArrange(int n) {
        if (n <= 0) {
            return;
        }
        boolean isSingle = (n & 1) == 1;
        int arraySize = isSingle ? n / 2 + 1 : n / 2;
        byte[] data = new byte[arraySize];

        fullArrangeRecursively(data, isSingle, arraySize);
    }

    /**
     * 递归每位全排位
     *
     * @param index 当前位置
     */
    private static void fullArrangeRecursively(byte[] data, boolean isSingle, int index) {
        // 如果是第一遍递归的话, 这里的data内还没有值, 所以需要把递归次数+1, 即index从data.length开始, 而不是原本的第一高位data.length-1
        if (index == 0) {
            System.out.println(getStringFromBytes(data));
            return;
        }
        int maxNum = (isSingle && index == data.length) ? 10 : 100;
        for (byte i = 0; i < maxNum; i++) {
            data[index - 1] = i;
            fullArrangeRecursively(data, isSingle, index - 1);
        }
    }

    /**
     * 打印1, 2, 3, ... , n位最大数
     * 用byte数组模拟+1操作实现
     */
    private static void print1ToMaxNumber(int n) {
        if (n <= 0) {
            return;
        }
        int arraySize = (n & 1) == 1 ? n / 2 + 1 : n / 2;
        byte[] data = new byte[arraySize];
        while (increment(data, (n & 1) == 1)) {
            System.out.println(getStringFromBytes(data));
        }
    }

    /**
     * byte数组转string
     */
    private static String getStringFromBytes(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] == 0 && builder.length() == 0) {
                continue;
            }
            if (data[i] < 10 && builder.length() != 0) {
                builder.append(0);
            }
            builder.append(data[i]);
        }
        return builder.toString();
    }

    /**
     * @param isSingleAtLast 最后一位最大是单位9
     * @return 是否没有溢出
     */
    private static boolean increment(byte[] data, boolean isSingleAtLast) {
        boolean notOverflow = true; // 没有溢出
        int position = 0; // 当前计算的位置
        boolean carry = true; // 是否有进位
        boolean isSingle; // 表明是不是"一位"的最后一位
        while (carry) {
            isSingle = isSingleAtLast && position == data.length - 1;
            if ((isSingle && data[position] < 9) || (!isSingle && data[position] < 99)) {
                data[position]++;
                carry = false;
            } else if (isSingle) {
                notOverflow = false;
            } else {
                data[position] = 0;
                position++;
            }
        }
        return notOverflow;
    }

    /**
     * 基本类型位数
     */
    private static void dataType() {
        // 有符号8位, 最大值为pow(2, 7) - 1 = 127
        byte a = 127;
        // 有符号16位, 最大值为pow(2, 15) - 1 = 32767
        short b = 32767;
        // 有符号32位
        int c = 2147483647;
        // 有符号64位
        long d = 1111111111111111111L;

        // 8位
        boolean i = false;
        // 无符号16位, 最大值位pow(2, 16) - 1 = 65536
        char e = 65535;
        // 有符号32位, 1位符号位, 8位指数位, 23位尾数位
        float f = -111111111111111111111111111111111111111F;
        // 有符号64位, 1位符号位, 11位指数位, 52位尾数位
        double g = -111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111D;

        System.out.println("byte:" + a + "; e:" + (char) 54);

        int[] ints = {1};
        ints[0]++;
        System.out.println("ints[0]++:" + ints[0]);
    }

    /**
     * 剪绳子, 长度为n的绳子剪至少一刀后, 各段绳子的最大乘积
     * <p>
     * 动态规划算法: 分解为子问题自上而下求解, 因为子问题会重复, 所以暂存子问题最优解, 避免重复计算
     */
    private static int cutRopeByDynamicProgramming(int length) {
        if (length < 2) {
            return 0;
        }
        if (length == 3) {
            return 2;
        }
        if (length == 4) {
            return 4;
        }

        int[] child = new int[length + 1]; // +1是因为多存储一个0
        child[1] = 1;
        child[2] = 2;
        child[3] = 3;
        child[4] = 4;

        int max;
        for (int i = 5; i <= length; i++) {
            // 长度为i时, 切为j*(i-j)两段, 计算最大值
            max = 0;
            // j从1开始是因为至少剪一刀
            for (int j = 1; j <= i / 2; j++) {
                max = Math.max(max, child[j] * child[i - j]);
            }
            // 把子问题的最优解暂存, 以便下阶段使用
            child[i] = max;
        }

        return child[length];
    }

    /**
     * 贪婪算法
     * 事先通过数学办法推算出最优算法
     */
    private static int cutRopeByGreedy(int length) {
        if (length < 2) {
            return 0;
        }
        if (length == 3) {
            return 2;
        }
        if (length == 4) {
            return 4;
        }

        int numOf3 = length / 3;
        int extra = length % 3;
        if (extra == 0) {
            return (int) Math.pow(3, numOf3);
        } else if (extra == 1) {
            return (int) Math.pow(3, numOf3 - 1) * 4;
        } else {
            return (int) Math.pow(3, numOf3) * 2;
        }
    }

    /**
     * 回溯法, 机器人能够到达多少个格子
     * 剑指offer p92
     *
     * @param limit 可移动的格子的行列的各位相加的最大值
     */
    private static int moveNum(int limit, int rows, int cols) {
        if (limit < 0 || rows < 1 || cols < 1) {
            return 0;
        }
        boolean[][] visited = new boolean[rows][cols];
        return movingCore(limit, rows, cols, visited, 0, 0);
    }

    private static int movingCore(int limit, int rows, int cols, boolean[][] visited, int row, int col) {
        int count = 0;
        if (row >= 0 && col >= 0 && row < rows && col < cols && !visited[row][col] && getDigitSum(row) + getDigitSum(col) <= limit) {
            count++;
            visited[row][col] = true;
            count += movingCore(limit, rows, cols, visited, row + 1, col);
            count += movingCore(limit, rows, cols, visited, row - 1, col);
            count += movingCore(limit, rows, cols, visited, row, col + 1);
            count += movingCore(limit, rows, cols, visited, row, col - 1);
        }
        return count;
    }

    private static int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    /**
     * 回溯法, 矩阵中是否存在路径
     * 剑指offer p89
     */
    private static boolean containsPath(char[][] matrix, char[] str) {
        if (matrix == null || str == null || matrix.length < 1 || str.length < 1 || matrix[0].length < 1) {
            return false;
        }

        boolean[][] visited = new boolean[matrix.length][matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                // 找到str第一个点在数组的位置
                if (str[0] == matrix[row][column]) {
                    if (containsPathCore(matrix, str, row, column, 0, visited)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 校验row/column当前点, 并递归判断其四个方位的点
     *
     * @param row    定位当前要判断的点
     * @param column 定位当前要判断的点
     */
    private static boolean containsPathCore(char[][] matrix, char[] str, int row, int column, int current, boolean[][] visited) {
        // 结束
        if (current >= str.length) {
            return true;
        }
        // 排除边界/已访问的点/不匹配的点
        if (row < 0 || row >= matrix.length || column < 0 || column >= matrix[row].length || current < 0
                || visited[row][column] || matrix[row][column] != str[current]) {
            return false;
        }
        current++;
        visited[row][column] = true;
        if (containsPathCore(matrix, str, row - 1, column, current, visited)
                || containsPathCore(matrix, str, row + 1, column, current, visited)
                || containsPathCore(matrix, str, row, column - 1, current, visited)
                || containsPathCore(matrix, str, row, column + 1, current, visited)) {
            return true;
        } else {
            visited[row][column] = false;
            return false;
        }
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
        int low = 0, height = array.length - 1, middle = (height + low) / 2;
        while (height - low > 1) {
            if (array[low] == array[height] && array[low] == array[middle]) {
                int min = array[low];
                for (int i = low + 1; i <= height; i++) {
                    if (array[i] < min) {
                        min = array[i];
                    }
                }
                return min;
            }
            if (array[middle] <= array[low] && array[middle] <= array[height]) {
                height = middle;
            }
            if (array[middle] >= array[low] && array[middle] >= array[height]) {
                low = middle;
            }
            middle = (low + height) / 2;
        }
        // 终止了循环, 即height - low <= 1
        return array[low] > array[height] ? array[height] : array[low];
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
     * 快速排序O(n*logn)
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
        int i = (int) (Math.random() * (high - low)) + low;
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
        // 执行到这里说明该节点是父节点的右子节点, 那么其下一个节点就是其前辈节点中第一个是其父节点的左子节点的那一个前辈节点的父节点
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
