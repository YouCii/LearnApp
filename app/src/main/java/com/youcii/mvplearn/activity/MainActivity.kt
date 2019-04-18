package com.youcii.mvplearn.activity

import android.os.Bundle

import com.google.android.material.tabs.TabLayout

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.orhanobut.logger.Logger
import com.youcii.mvplearn.R
import com.youcii.mvplearn.activity.interfaces.IMainView
import com.youcii.mvplearn.adapter.MainPagerAdapter
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.fragment.ItemFragment
import com.youcii.mvplearn.fragment.RxFragment
import com.youcii.mvplearn.fragment.SocketFragment
import com.youcii.mvplearn.fragment.WebFragment
import com.youcii.mvplearn.model.MainMenuID
import com.youcii.mvplearn.utils.ToastUtils
import com.youcii.mvplearn.widget.GuideView

import java.util.ArrayList

import butterknife.ButterKnife
import com.youcii.mvplearn.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 个人理解: MVP中的view层主要是提供给presenter使用, 如果没有presenter, 没有必要创建View层
 */
class MainActivity : BaseActivity(), IMainView, View.OnClickListener, ViewPager.OnPageChangeListener {
    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()

    private var guideRx: GuideView? = null
    private var guideFB: GuideView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("Main--onCreate")
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        initToolBar("主页")

        initFragmentList()
        fab!!.setOnClickListener(this)

        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, fragmentList, titleList)
        mainViewpager!!.adapter = mainPagerAdapter
        mainViewpager!!.addOnPageChangeListener(this)

        mainTabLayout!!.tabMode = TabLayout.MODE_FIXED
        mainTabLayout!!.setupWithViewPager(mainViewpager)

        var imageView = ImageView(this)
        imageView.setImageResource(R.drawable.selector_main_tap_call)
        mainTabLayout!!.getTabAt(0)!!.customView = imageView
        imageView = ImageView(this)
        imageView.setImageResource(R.drawable.selector_main_tap_conversation)
        mainTabLayout!!.getTabAt(1)!!.customView = imageView
        imageView = ImageView(this)
        imageView.setImageResource(R.drawable.selector_main_tap_contact)
        mainTabLayout!!.getTabAt(2)!!.customView = imageView
        imageView = ImageView(this)
        imageView.setImageResource(R.drawable.selector_main_tap_plugin)
        mainTabLayout!!.getTabAt(3)!!.customView = imageView

        mainTabLayout!!.getTabAt(0)!!.customView!!.isSelected = true

        initGuideView()
    }

    /**
     * 初始化GuideView
     */
    private fun initGuideView() {
        // 使用图片
        val iv = ImageView(this)
        iv.setImageResource(R.mipmap.ic_launcher)
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        iv.layoutParams = params

        // 使用文字
        val tv = TextView(this)
        tv.text = "更多功能"
        tv.setTextColor(ResourcesUtils.getColor(this, R.color.white))
        tv.textSize = 30f
        tv.gravity = Gravity.CENTER

        guideRx = GuideView.Builder.newInstance(this)
                .setTargetView(toolBar!!.getChildAt(0))
                .setCustomGuideView(iv)
                .setDirction(GuideView.Direction.RIGHT_BOTTOM)
                .setShape(GuideView.MyShape.CIRCULAR)
                .setBgColor(GuideView.DEFAULT_MASK_COLOR)
                .showOnce()
                .setOnclickListener {
                    guideRx!!.hide()
                    guideFB!!.show()
                }
                .build()

        guideFB = GuideView.Builder.newInstance(this)
                .setTargetView(fab)
                .setCustomGuideView(tv)
                .setDirction(GuideView.Direction.LEFT_TOP)
                .setShape(GuideView.MyShape.ELLIPSE)
                .setBgColor(GuideView.DEFAULT_MASK_COLOR)
                .showOnce()
                .setOnclickListener { guideFB!!.hide() }
                .build()

        guideRx!!.show()
    }

    override fun initFragmentList() {
        fragmentList.add(SocketFragment())
        fragmentList.add(RxFragment())
        fragmentList.add(ItemFragment())
        fragmentList.add(WebFragment())

        titleList.add("Socket通信")
        titleList.add("RxJava示例")
        titleList.add("多样式RecyclerView")
        titleList.add("原生与html互相调用")
    }

    override fun initToolBar(title: String) {
        toolBar!!.title = title
        toolBar!!.setNavigationIcon(R.drawable.toolbar_back)
        setSupportActionBar(toolBar)

        toolBar!!.setNavigationOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        centerButtonInit(menu) // 方式2：getMenuInflater().inflate(R.menu.menu_main, menu); // 通过布局定义
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 初始化各个按键参数
     */
    private fun centerButtonInit(menu: Menu) {
        menu.clear()

        menu.add(0, MainMenuID.设置, 0, "设置")
        menu.add(0, MainMenuID.关于, 0, "关于")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MainMenuID.设置 || item.itemId == R.id.action_settings) {
            showToast("设置")
            return true
        }
        if (item.itemId == MainMenuID.关于 || item.itemId == R.id.action_about) {
            showToast("关于")
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showToast(content: String) {
        ToastUtils.showShortToast(content)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.fab) {
            TestListActivity.startActivity(this)
        } else {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        Logger.i("Main--onResume")
    }

    override fun onStart() {
        super.onStart()
        Logger.i("Main--onStart")
    }

    override fun onPause() {
        super.onPause()
        Logger.i("Main--onPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.i("Main--onStop")
        if (isFinishing) {
            // 不在onDestroy里处理停止thread等释放资源的处理, 因为onDestroy执行较晚
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("Main--onDestroy")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        toolBar!!.title = titleList[position]
    }

    override fun onPageScrollStateChanged(state: Int) {}

}
