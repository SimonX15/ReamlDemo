package com.app.simon.realmdemo

import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * desc: RealmHelper
 * date: 2017/9/19

 * @author xw
 */
object RealmHelper {
    private val TAG = RealmHelper::class.java.simpleName
    private val REALM_NAME = "my_realm.realm"

    /** 获取realm对象 */
    fun getRealm(): Realm {
        val configuration = RealmConfiguration.Builder()
                .name(REALM_NAME) //文件名
//                    .schemaVersion(0) //版本号
                /*.schemaVersion(1) //版本号升级
                .migration { realm, oldVersion, newVersion ->
                    Log.i(TAG, "oldVersion:$oldVersion;newVersion:$newVersion")
                    if (oldVersion == 0L) {
                        val schema = realm.schema
                        //dog
                        val dogSchema = schema.get("Dog")
                        //已经新建了Dog类，就不需要create，只需要get就行了
//                            val dogSchema = schema.create("Dog")
//                            dogSchema.addField("name", String::class.java)
//                            dogSchema.addField("age", Int::class.java)

                        val userSchema = schema.get("User")
                        userSchema
                                .addRealmObjectField("dog", dogSchema)
                    }
                }*/
                .schemaVersion(2) //版本号升级
//                    .deleteRealmIfMigrationNeeded()//删除原数据
                .migration { realm, oldVersion, newVersion ->
                    Log.i(TAG, "oldVersion:$oldVersion;newVersion:$newVersion")
                    val schema = realm.schema
                    if (oldVersion == 0L) {
                        //dog
                        val dogSchema = schema.get("Dog")

                        val userSchema = schema.get("User")
                        userSchema
                                .addRealmObjectField("dog", dogSchema)
                                .transform {
                                    if (it.getString("name") == "赵云") {
                                        val dog = realm.createObject("Dog")
                                        dog.setString("name", "啸天")
                                        dog.setInt("age", 3)
                                        it.set("dog", dog)
                                    }
                                }
                    }
                    if (oldVersion == 1L) {
                        //cat
                        val catSchema = schema.get("Cat")

                        val userSchema = schema.get("User")
                        userSchema
                                .addRealmListField("catList", catSchema)
                                .transform {
                                    if (it.getString("name") == "张飞") {
                                        val cat = realm.createObject("Cat")
                                        cat.setString("name", "喵喵")
                                        cat.setInt("age", 2)

                                        it.getList("catList").add(cat)
                                    }
                                }
                    }
                }
                .build()
        Realm.setDefaultConfiguration(configuration)
        return Realm.getInstance(configuration)!!
    }


}
