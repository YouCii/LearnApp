package com.jdw.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jdw
 * @date 2017/7/24
 * <p>
 * edit at 2017/12/28 修改为了可复用的GridView + ViewPager;
 * edit at 2018/1/24 各页GridView使用不同的布局
 */
class PagerGridAdapter extends PagerAdapter {
    private Context context;

    private CopyOnWriteArrayList<PagerGridBean> dataList;
    private List<RecyclerView> gridViewList = new ArrayList<>();

    PagerGridAdapter(Context context, CopyOnWriteArrayList<PagerGridBean> list) {
        this.context = context;
        this.dataList = list;
        initData();
    }

    private void initData() {
        gridViewList.clear();
        for (PagerGridBean pagerGridBean : dataList) {
            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setLayoutManager(new GridLayoutManager(context, pagerGridBean.lineSize)); // 列数
            recyclerView.addItemDecoration(new SpacesItemDecoration(15));
            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 去掉滑动到顶部底部时的水波纹效果
            recyclerView.setHasFixedSize(true); // 如果item大小固定时可加此句, 提高绘制效率
            recyclerView.setAdapter(new SwitchAdapter(context, pagerGridBean));
            gridViewList.add(recyclerView);
        }
    }

    @Override
    public int getCount() {
        return gridViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(gridViewList.get(position));
        return gridViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(gridViewList.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
