package com.jdw.widget;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int right, bottom;

    SpacesItemDecoration(int right, int bottom) {
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = right;
        outRect.bottom = bottom;
    }
}