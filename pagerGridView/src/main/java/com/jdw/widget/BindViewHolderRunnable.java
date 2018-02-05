package com.jdw.widget;

/**
 * recycler 构建View, 设置监听等操作
 *
 * @author jdw
 */
public interface BindViewHolderRunnable {
    /**
     * recycler 构建View, 设置监听等操作
     *
     * @param holder holder
     * @param object 当前位置的数据
     */
    void onBindViewHolder(SwitchAdapter.ViewHolder holder, Object object);
}