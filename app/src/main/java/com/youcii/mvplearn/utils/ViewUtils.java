package com.youcii.mvplearn.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

/**
 * Created by YouCii on 2016/7/18.
 */
public class ViewUtils {

    /* 隐藏输入法 */
    public static void hideInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = ((Activity) context).getCurrentFocus();
        if (imm.isActive() && view != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* 设置listview高度 */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /* 给某控件添加阴影效果 */
    public static void addViewShadow(View view) {
        ShadowProperty shadowProperty = new ShadowProperty();
        shadowProperty.setShadowColor(0x77000000).setShadowDy(2).setShadowRadius(3);
        ShadowViewHelper.bindShadowHelper(shadowProperty, view);
    }

}
