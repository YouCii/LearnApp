package com.jdw.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int right, bottom;

    public SpacesItemDecoration(int right, int bottom) {
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = right;
        outRect.bottom = bottom;
    }
}