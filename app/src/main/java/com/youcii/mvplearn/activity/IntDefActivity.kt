package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.model.InstitutionEnum
import kotlinx.android.synthetic.main.activity_int_def.*

class IntDefActivity : BaseActivity() {

    private var currentDay = InstitutionEnum.BANK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_int_def)

        currentDay = intent.getIntExtra(INTENT_KEY, InstitutionEnum.BANK)

        when (currentDay) {
            InstitutionEnum.BANK -> textView.text = currentDay.toString()
            InstitutionEnum.HOTEL -> textView.text = currentDay.toString()
            InstitutionEnum.SHOP -> textView.text = currentDay.toString()
            else -> {
            }
        }

    }

    companion object {
        private const val INTENT_KEY = "enum"

        @JvmStatic
        fun startActivity(context: Context, @InstitutionEnum enum: Int) {
            val intent = Intent(context, IntDefActivity::class.java)
            intent.putExtra(INTENT_KEY, enum)
            context.startActivity(intent)
        }
    }
}
