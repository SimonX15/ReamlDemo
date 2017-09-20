package com.app.simon.realmdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        assignViews()
    }

    private fun assignViews() {
        btn_add.setOnClickListener {
            val user1 = User()
            user1.id = 101
            user1.name = "赵云"

            val user2 = User()
            user2.id = 102
            user2.name = "张飞"
            RealmHelper.getRealm().executeTransactionAsync({
                it.copyToRealmOrUpdate(user1)
                it.copyToRealmOrUpdate(user2)
            }, {
                tv_result.text = "add success"
                Log.i(TAG, "add success")
            }, {
                tv_result.text = "add error"
                Log.i(TAG, "add error")
            })
            /*RealmHelper.getRealm().executeTransaction {
                val user1 = it.createObject(User::class.java)
                user1.id = 101
                user1.name = "赵云"
            }*/

            SecondActivity.launch(this@MainActivity)
        }
        btn_delete.setOnClickListener {
            RealmHelper.getRealm().executeTransaction {
                val results = it.where(User::class.java)
                        .equalTo("name", "张飞")
                        .findAll()
                results.deleteAllFromRealm()
                tv_result.text = "delete success"
            }
        }
        btn_update.setOnClickListener {
            RealmHelper.getRealm().executeTransaction {
                val user = it.where(User::class.java)
                        .equalTo("name", "张飞")
                        .findFirst()
                user?.name = "李四"

                tv_result.text = "update success"
            }
        }
        btn_query.setOnClickListener {
            RealmHelper.getRealm()
                    .where(User::class.java)
//                    .equalTo("name", "赵云")
                    .findAllAsync()
                    .addChangeListener {
                        tv_result.text = "query$it"
                        Log.i(TAG, "query it=" + it.toString())
                    }
        }
    }

    private fun init() {
        Realm.init(this)
//        val realm = Realm.getDefaultInstance()
        /*val config = RealmConfiguration.Builder()
                .name("my_realm.realm") //文件名
                .schemaVersion(0) //版本号
                .build()*/
//        RealmHelper.getRealm()
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmHelper.getRealm().close()
    }
}
