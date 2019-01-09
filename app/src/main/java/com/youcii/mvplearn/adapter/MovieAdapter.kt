package com.youcii.mvplearn.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youcii.mvplearn.app.App
import com.youcii.mvplearn.model.MovieEntity
import com.youcii.mvplearn.utils.ToastUtils

/**
 * Created by jdw on 2018/4/8.
 */
class MovieAdapter(private val activity: Activity, private val dataList: ArrayList<MovieEntity>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = ImageView(activity)
        imageView.layoutParams = ViewGroup.LayoutParams(App.getScreenWidth() / 4, App.getScreenWidth() / 4 / 27 * 40)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ViewHolder(imageView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(activity)
                .load(dataList[position].images.large)
                .into(holder.itemView as ImageView)

        holder.itemView.setOnClickListener { ToastUtils.showShortToast(dataList[position].title) }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

}