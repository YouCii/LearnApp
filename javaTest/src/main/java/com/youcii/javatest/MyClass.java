package com.youcii.javatest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdw on 2018/12/20.
 * <p>
 * 用于LeetCode的测试
 */
public class MyClass {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

    /**
     * 无重复字符的最长子串的长度
     *
     * 滑动窗口, 以begin/end为两端进行检测, 当end字符在窗口中已存在时, 把begin跳转到前者+1的位置, end继续向后, 直到end到达string最后一位.
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
