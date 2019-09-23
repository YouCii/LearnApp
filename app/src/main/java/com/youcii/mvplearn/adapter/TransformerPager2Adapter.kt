package com.youcii.mvplearn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youcii.mvplearn.R
import kotlinx.android.synthetic.main.item_transformer_pager2.view.*

/**
 * Created by jdw on 2019/09/09.
 */
class TransformerPager2Adapter(private val context: Context, private val imageList: List<String>) : RecyclerView.Adapter<MyHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(layoutInflater.inflate(R.layout.item_transformer_pager2, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(context).load(imageList[position]).into(holder.itemView.ivContent)
    }
}

class MyHolder(contentView: View) : RecyclerView.ViewHolder(contentView)