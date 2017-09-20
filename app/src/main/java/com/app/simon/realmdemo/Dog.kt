package com.app.simon.realmdemo

import io.realm.RealmModel
import io.realm.annotations.RealmClass

/**
 * desc:
 * date: 2017/9/20

 * @author xw
 */
@RealmClass
open class Dog : RealmModel {

    var name: String? = null
    var age: Int = 0
}
