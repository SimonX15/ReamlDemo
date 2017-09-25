package com.app.simon.realmdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
//        val realm = RealmHelper.getRealm()
        btn_add.setOnClickListener {
            val user1 = User()
            user1.id = 101
            user1.name = "赵云"
            user1.age = 23
            val dog = Dog()
            dog.name = "小黑"
            dog.age = 2
//            user1.dog = dog

            val user2 = User()
            user2.id = 102
            user2.name = "张飞"
            user2.age = 24
//            user2.catList = RealmList()
//            val cat = Cat()
//            cat.name = "喵喵"
//            cat.age = 1
//            user2.catList!!.add(cat)


            val user3 = User()
            user3.name = "李四"
            user3.age = 30

            val user4 = User()
            user4.name = "江姐"
            user4.age = 30

            val realm = RealmHelper.getRealm()
            realm.executeTransactionAsync({
                it.copyToRealmOrUpdate(user1)
                it.copyToRealmOrUpdate(user2)
                it.copyToRealmOrUpdate(user3)
                it.copyToRealmOrUpdate(user4)
            }, {
                tv_result.text = "add success"
                Log.i(TAG, "add success")
                realm.close()
            }, {
                tv_result.text = "add error"
                Log.e(TAG, "add error", it)
                realm.close()
            })
        }
        btn_delete.setOnClickListener {
            UserDBHelper.deleteByName("张飞", object : OnDBCompleteListener {
                override fun onResult(results: List<Any>?) {

                }

                override fun onSuccess() {
                    Log.i(TAG, "delete success")
                }

                override fun onError(throwable: Throwable) {
                    Log.e(TAG, "delete error", throwable)
                }

            })
        }
        btn_update.setOnClickListener {
            val realm = RealmHelper.getRealm()
            realm.executeTransaction {
                val user = it.where(User::class.java)
                        .equalTo("name", "张飞")
                        .findFirst()
                user?.name = "李四"

                tv_result.text = "update success"
            }
            realm.close()
        }
        btn_query.setOnClickListener {
            val realm = RealmHelper.getRealm()
            realm
                    .where(User::class.java)
//                    .equalTo("name", "赵云")
                    .findAllAsync()
                    .addChangeListener {
                        tv_result.text = "query$it"
                        Log.i(TAG, "query it=" + it.toString())

                        val list = realm.copyFromRealm(it)
                        Log.i(TAG, "query list=" + list.toString())
                        realm.close()
                    }
        }
    }

    private fun init() {

    }

    override fun onDestroy() {
        super.onDestroy()
//        RealmHelper.getRealm().close()
    }
}
