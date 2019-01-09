package com.youcii.mvplearn.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.MultiTypeAdapter
import com.youcii.mvplearn.base.BaseFragment
import com.youcii.mvplearn.fragment.interfaces.IFragItemView
import com.youcii.mvplearn.model.SkinInfoBean
import kotlinx.android.synthetic.main.fragment_item.*
import java.util.*

class ItemFragment : BaseFragment(), IFragItemView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onResume() {
        super.onResume()
        val adapter = MultiTypeAdapter(context!!, showDate)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == MultiTypeAdapter.SKIN) 1 else 2
            }
        }
        list.layoutManager = layoutManager
        list.adapter = adapter
    }

    override fun getShowDate(): List<Any> {
        val list = ArrayList<Any>()
        list.add("极简颜色")
        list.add(SkinInfoBean("天蓝", "0"))
        list.add(SkinInfoBean("砖红", "1"))
        list.add("特色主题")
        list.add(SkinInfoBean("惬意时光", "http://5b0988e595225.cdn.sohucs.com/images/20180203/bb9208badf9e4fdd9a46e7a1243f9c46.jpeg"))
        list.add(SkinInfoBean("新年快乐", "http://img5.imgtn.bdimg.com/it/u=2198448186,2593700097&fm=26&gp=0.jpg"))
        return list
    }

}
