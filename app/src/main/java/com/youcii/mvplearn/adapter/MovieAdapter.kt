package com.youcii.mvplearn.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.youcii.mvplearn.app.App
import com.youcii.mvplearn.model.MovieEntity
import com.youcii.mvplearn.utils.DiskLruCacheUtils
import com.youcii.mvplearn.utils.ToastUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

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
        val url = dataList[position].images.large
        Glide.with(activity)
                .load(url)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        holder.itemView.background = resource

                        val stream = ByteArrayOutputStream()
                        (resource as BitmapDrawable).bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        DiskLruCacheUtils.save(url, ByteArrayInputStream(stream.toByteArray()))
                    }
                })

        holder.itemView.setOnClickListener { ToastUtils.showShortToast(dataList[position].title) }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

}