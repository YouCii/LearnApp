package com.youcii.javatest.structure;

public class LinkedNode {
    public int val;
    public LinkedNode next;

    public LinkedNode(int x) {
        val = x;
    }

    // 遍历链表返回拼接字符串
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(val);
        LinkedNode current = next;
        while (current != null) {
            builder.append("->").append(current.val);
            current = current.next;
            if (current == this) {
                builder.append("->ring");
                break;
            }
        }
        return builder.toString();
    }
}