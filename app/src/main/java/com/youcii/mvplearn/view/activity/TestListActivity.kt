package com.youcii.mvplearn.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test_list.*

class TestListActivity : BaseActivity(), AdapterView.OnItemClickListener {

    internal val testArray: ArrayList<String>? = ArrayList()  // internal 私有，常量，如果不写默认是 public, 必须初始化

    override fun onCreate(savedInstanceState: Bundle?) { // 取消@Override，而是方法前override
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)

        initArray()

        val view1 = View(this) // 常量
        view1.layoutParams = AbsListView.LayoutParams(MATCH_PARENT, 50) // set的简写
        view1.setBackgroundResource(R.color.black)
        lv_test.addHeaderView(view1, null, true) // !!代表会抛出空异常
        val view2 = View(this)
        view2.layoutParams = AbsListView.LayoutParams(MATCH_PARENT, 50)
        view2.setBackgroundResource(R.color.pink)
        lv_test.addHeaderView(view2, null, true)

        lv_test.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, testArray) // 不再使用 new
        lv_test.onItemClickListener = this // set的简写
    }

    private fun initArray() {
        testArray?.add("列表刷新") // ? 代表自动检测空，如果为空则跳过，不抛出异常
        testArray?.add("接口压力测试")
        testArray?.add("diyView测试")

        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")
        testArray?.add("111")

    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (position) { // when 方法，替代which，可以使用 in 等更方便的判断
            2 -> ListRefreshActivity.startActivity(this)
            3 -> HttpTestActivity.startActivity(this)
            4 -> DiyViewActivity.startActivity(this)
            else -> {
            }
        }
    }

    companion object {
        fun startActivity(context: Context?) {
            val intent = Intent(context, TestListActivity::class.java)
            context?.startActivity(intent)
        }
    }

}
