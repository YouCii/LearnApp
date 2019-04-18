package com.jdw.widget;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jdw
 * @date 2018/2/1
 */
public class PagerGridView extends FrameLayout {

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    private PagerGridAdapter pagerGridAdapter;
    private CopyOnWriteArrayList<PagerGridBean> dataList;

    private ViewPager.OnPageChangeListener onPageChangeListener;

    private boolean cycleScroll;


    public PagerGridView(Context context) {
        this(context, null);
    }

    public PagerGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PagerGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_pager_grid, this);

        viewPager = findViewById(R.id.view_pager);
        radioGroup = findViewById(R.id.radio_group);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerGridView, defStyleAttr, defStyleRes);
        cycleScroll = typedArray.getBoolean(R.styleable.PagerGridView_cycleScroll, false);
        typedArray.recycle();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                // 如果是循环滚动的话
                if (cycleScroll && dataList.size() > 1) {
                    if (position == 0) {
                        viewPager.setCurrentItem(dataList.size() - 2);
                        if (radioGroup.getChildCount() > dataList.size() - 3) {
                            ((RadioButton) radioGroup.getChildAt(dataList.size() - 3)).setChecked(true);
                        }
                    } else if (position == dataList.size() - 1) {
                        viewPager.setCurrentItem(1);
                        if (radioGroup.getChildCount() > 0) {
                            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
                        }
                    } else {
                        if (radioGroup.getChildCount() > position - 1) {
                            ((RadioButton) radioGroup.getChildAt(position - 1)).setChecked(true);
                        }
                    }
                }
                // 不循环滚动
                else {
                    ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
                }

                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        viewPager.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public void setPagerAdapter(Context context, CopyOnWriteArrayList<PagerGridBean> list) {
        this.dataList = list;
        refreshStrip();

        // 处理首尾
        if (cycleScroll && dataList.size() > 1) {
            dataList.add(0, dataList.get(dataList.size() - 1));
            dataList.add(dataList.get(1));
        }

        pagerGridAdapter = new PagerGridAdapter(context, dataList);
        viewPager.setAdapter(pagerGridAdapter);

        if (cycleScroll && dataList.size() > 1) {
            viewPager.setCurrentItem(1);
        }
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.onPageChangeListener = listener;
    }

    private void refreshStrip() {
        // 下方的切换指示器
        radioGroup.removeAllViews();
        int pageNum = dataList.size();
        if (pageNum > 1) {
            while (pageNum-- > 0) {
                LayoutInflater.from(getContext()).inflate(R.layout.view_strip_point, radioGroup, true);
            }
            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        }
    }

}
