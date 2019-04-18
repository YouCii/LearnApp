package com.youcii.mvplearn.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youcii.mvplearn.R
import kotlinx.android.synthetic.main.item_transformer_pager.view.*

/**
 * Created by jdw on 2018/11/1.
 *
 * 露边动画效果ViewPager的adapter
 */
class TransformerPagerAdapter(private val context: Context, private val imgList: List<Drawable>) : androidx.viewpager.widget.PagerAdapter() {

    override fun getCount(): Int {
        return imgList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_transformer_pager, container, false)
        container.addView(itemView)

        itemView.iv_skin.background = imgList[position]
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}