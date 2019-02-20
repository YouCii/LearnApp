package com.youcii.javatest;

class LinkedNode {
    int val;
    LinkedNode next;

    LinkedNode(int x) {
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
        }
        return builder.toString();
    }
}