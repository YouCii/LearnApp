package com.jdw.widget;

import android.support.annotation.LayoutRes;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用于构建各页均不相同的gridView
 *
 * @author jdw
 * @date 2018/1/24
 */
public class PagerGridBean {
    /**
     * 真正的adapter数据
     */
    public CopyOnWriteArrayList list;
    /**
     * 当前页的布局
     */
    @LayoutRes
    int itemView;
    /**
     * 每行的item个数
     */
    int lineSize;
    /**
     * 行数
     */
    int lineNum;
    /**
     * onBindViewHolder 处理时需要的操作
     */
    BindViewHolderRunnable bindViewHolderRunnable;

    public PagerGridBean(CopyOnWriteArrayList list, int itemView, int lineSize, int lineNum, BindViewHolderRunnable bindViewHolderRunnable) {
        this.list = list;
        this.itemView = itemView;
        this.lineSize = lineSize;
        this.lineNum = lineNum;
        this.bindViewHolderRunnable = bindViewHolderRunnable;
    }
}