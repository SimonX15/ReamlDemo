package com.app.simon.realmdemo

import android.app.Application
import io.realm.Realm

/**
 * desc: TheApplication
 * date: 2017/9/20

 * @author xw
 */
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
    }

}
