package com.app.simon.realmdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    companion object {
        private val TAG = SecondActivity::class.java.simpleName

        fun launch(activity: Activity) {
            val intent = Intent(activity, SecondActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
