package com.youcii.mvplearn.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.youcii.mvplearn.R
import com.youcii.mvplearn.model.SkinInfoBean
import com.youcii.mvplearn.utils.ResourcesUtils
import kotlinx.android.synthetic.main.item_skin.view.*

/**
 * Created by jdw on 2018/10/31.
 *
 * 多类型混合spanSize的Adapter
 */
class MultiTypeAdapter(private val context: Context, private val dataList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when {
            dataList[position] is String ->
                TITLE
            dataList[position] is SkinInfoBean ->
                SKIN
            else ->
                ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SKIN -> SkinHolder(inflater.inflate(R.layout.item_skin, parent, false))
            else -> TitleHolder(inflater.inflate(R.layout.item_skin_title, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleHolder -> (holder.itemView as TextView).text = dataList[position] as String
            is SkinHolder -> {
                val skinBean = dataList[position] as SkinInfoBean

                if (skinBean.picture!!.startsWith("http")) {
                    Glide.with(context).load(skinBean.picture).into(holder.itemView.iv_skin_icon)
                } else {
                    holder.itemView.iv_skin_icon.setBackgroundColor(ResourcesUtils.getColor(context, if (position == 1) R.color.red else R.color.blue))
                }
                holder.itemView.tv_skin_name.text = skinBean.name
                holder.itemView.setOnClickListener { }
            }
        }
    }

    private class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private class SkinHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * ItemType
     */
    companion object {
        const val TITLE = 0
        const val SKIN = 1
        const val ERROR = 10
    }
}
