package com.app.simon.realmdemo

import android.annotation.SuppressLint
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * desc:
 * date: 2017/9/19

 * @author xw
 */
object RealmHelper {
    private val TAG = RealmHelper::class.java.simpleName

    @SuppressLint("StaticFieldLeak")
    private var realm: Realm? = null

    fun getRealm(): Realm {
        if (realm == null) {
            realm = Realm.getInstance(RealmConfiguration.Builder()
                    .name("my_realm.realm") //文件名
//                    .schemaVersion(0) //版本号
                    .schemaVersion(2) //版本号升级
                    .migration { realm, oldVersion, newVersion ->
                        Log.i(TAG, "oldVersion:$oldVersion;newVersion:$newVersion")
                        if (oldVersion == 1L && newVersion == 2L) {
                            val schema = realm.schema
                            //dog
                            val dogSchema = schema.create("Dog")
                            dogSchema.addField("name", String::class.java)
                            dogSchema.addField("age", Int::class.java)
                            //cat
                            val catSchema = schema.create("Cat")
                            catSchema.addField("name", String::class.java)
                            catSchema.addField("age", Int::class.java)

                            val userSchema = schema.get("User")
                            userSchema
                                    .addField("age", Int::class.java)
                                    .transform {
                                        it.set("name", "新人")
                                    }
                                    .addRealmObjectField("dog", dogSchema)
                                    .addRealmListField("catList", catSchema)

                        }
                    }
                    .build())
        }
        return realm!!
    }


}