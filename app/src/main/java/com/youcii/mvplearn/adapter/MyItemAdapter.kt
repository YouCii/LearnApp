package com.youcii.mvplearn.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.youcii.mvplearn.R
import com.youcii.mvplearn.model.RecyclerBean
import com.youcii.mvplearn.utils.ToastUtils


class MyItemAdapter(private val mValues: List<RecyclerBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == 2) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment, parent, false)
            MyItemViewHolder(view)
        } else {
            val imageView = ImageView(parent.context)
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            imageView.layoutParams = layoutParams
            object : RecyclerView.ViewHolder(imageView) {}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyItemViewHolder) {
            holder.mIdView.text = mValues[position].id
            holder.mContentView.text = mValues[position].content

            holder.mIdView.isSelected = true
            holder.mContentView.isSelected = true

            holder.itemView.setOnClickListener { _ ->
                ToastUtils.showShortToast("点击了 " + mValues[position].content)
            }
        } else {
            Glide.with(holder.itemView).load(mValues[position].content).into(holder.itemView as ImageView)
        }
    }

    inner class MyItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mIdView: TextView = view.findViewById(R.id.id) as TextView
        var mContentView: TextView = view.findViewById(R.id.content) as TextView
    }
}