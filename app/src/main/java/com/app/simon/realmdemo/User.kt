package com.app.simon.realmdemo

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

/**
 * desc: User
 * date: 2017/9/19

 * @author xw
 */
@RealmClass
open class User : RealmModel {
    /**
     * id : 101
     * name : laozhang
     */
    @PrimaryKey
    var id: Int = 0

    @Required
    var name: String? = null

    var age:Int = 0

//    var dog: Dog? = null

//    var catList: RealmList<Cat>? = null
}
