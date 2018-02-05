package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jdw.widget.BindViewHolderRunnable
import com.jdw.widget.PagerGridBean
import com.jdw.widget.SwitchAdapter
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pager_grid.*
import java.util.concurrent.CopyOnWriteArrayList

class PagerGridActivity : BaseActivity() {

    private val dataList: CopyOnWriteArrayList<PagerGridBean> = CopyOnWriteArrayList()
    private val drawableList: CopyOnWriteArrayList<Int> = CopyOnWriteArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_grid)

        var i = 0
        while (i < 10) {
            i++
            drawableList.add(R.mipmap.ic_launcher)
        }
        dataList.add(PagerGridBean(drawableList, R.layout.item_single_image, 5, 2, bindHolderRunnable))
        dataList.add(PagerGridBean(drawableList, R.layout.item_single_image, 5, 2, bindHolderRunnable))

        pagerGridView.setPagerAdapter(this, dataList)
    }

    private val bindHolderRunnable: BindViewHolderRunnable = BindViewHolderRunnable { viewHolder: SwitchAdapter.ViewHolder, drawableResource ->
        viewHolder.imageView.setBackgroundResource(drawableResource as Int)
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, PagerGridActivity::class.java))
        }
    }
}
