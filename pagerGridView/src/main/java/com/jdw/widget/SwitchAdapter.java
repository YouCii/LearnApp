package com.jdw.widget;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ViewPager内的小列表
 *
 * @author jdw
 */
public class SwitchAdapter extends RecyclerView.Adapter<SwitchAdapter.ViewHolder> {

    private Context context;

    /**
     * adapter数据
     */
    private CopyOnWriteArrayList list;
    /**
     * 当前页的布局
     */
    @LayoutRes
    private int itemView;
    /**
     * 每行的item个数
     */
    private int lineSize;
    /**
     * 行数
     */
    private int lineNum;
    /**
     * onBindViewHolder 处理时需要的操作
     */
    private BindViewHolderRunnable bindViewHolderRunnable;

    SwitchAdapter(Context context, PagerGridBean pagerGridBean) {
        this.context = context;

        this.list = pagerGridBean.list;
        this.itemView = pagerGridBean.itemView;
        this.lineSize = pagerGridBean.lineSize;
        this.lineNum = pagerGridBean.lineNum;
        this.bindViewHolderRunnable = pagerGridBean.bindViewHolderRunnable;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(itemView, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        return viewHolder;
    }

    /**
     * 此处必须显式声明 SwitchAdapter.ViewHolder holder, 否则编译失败
     */
    @Override
    public void onBindViewHolder(@NonNull SwitchAdapter.ViewHolder holder, int position) {
        bindViewHolderRunnable.onBindViewHolder(holder, list.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size() > lineNum * lineSize ? lineNum * lineSize : list.size();
    }

    /**
     * 一个ImageView一个TextView, 或者一个ImageView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView textView;

        ViewHolder(View convertView) {
            super(convertView);
            if (convertView instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) convertView;
                for (int i = 0; i < group.getChildCount(); i++) {
                    View view = group.getChildAt(i);
                    if (view instanceof ImageView) {
                        imageView = (ImageView) view;
                    } else if (view instanceof TextView) {
                        textView = (TextView) view;
                    }
                }
            } else if (convertView instanceof ImageView) {
                imageView = (ImageView) convertView;
            }
        }
    }


}