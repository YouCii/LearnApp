package com.youcii.mvplearn.greendao

import android.annotation.SuppressLint
import com.youcii.mvplearn.app.App

/**
 * Created by jdw on 2018/4/17.
 */
object DaoManager {

    @SuppressLint("StaticFieldLeak")
    private val devOpenHelper = DaoMaster.DevOpenHelper(App.getContext(), "GreenDaoDB", null)
    private val daoMaster = DaoMaster(devOpenHelper.writableDatabase)
    private val session = daoMaster.newSession()

    fun getMovieEntityDao(): MovieEntityDao {
        return session.movieEntityDao
    }

}