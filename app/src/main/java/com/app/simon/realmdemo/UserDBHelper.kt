package com.app.simon.realmdemo

/**
 * desc: UserDBHelper
 * date: 2017/9/25
 *
 * @author xw
 */
object UserDBHelper {

    /** 添加 */
    fun add(user: User, listener: OnDBCompleteListener) {
        val realm = RealmHelper.getRealm()
        realm.executeTransactionAsync({
            realm.copyToRealmOrUpdate(user)
        }, {
            listener.onSuccess()
            realm.close()
        }, {
            listener.onError(it)
            realm.close()
        })
    }

    /** 更新 */
    fun update(user: User, listener: OnDBCompleteListener) {
        //这样的方式是通过主id更新的
        add(user, listener)
    }

    /** 删除 */
    fun deleteById(id: Int, listener: OnDBCompleteListener) {
        val realm = RealmHelper.getRealm()
        realm.executeTransactionAsync({
            val results = it.where(User::class.java)
                    .equalTo("id", id)
                    .findAll()
            results.deleteAllFromRealm()
        }, {
            listener.onSuccess()
            realm.close()
        }, {
            listener.onError(it)
            realm.close()
        })
    }

    /** 删除 */
    fun deleteByName(name: String, listener: OnDBCompleteListener) {
        val realm = RealmHelper.getRealm()
        realm.executeTransactionAsync({
            val results = it.where(User::class.java)
                    .equalTo("name", name)
                    .findAll()
            results.deleteAllFromRealm()
        }, {
            listener.onSuccess()
            realm.close()
        }, {
            listener.onError(it)
            realm.close()
        })
    }

    /** 查找全部 */
    fun queryAll(listener: OnDBCompleteListener) {
        val realm = RealmHelper.getRealm()
        realm.executeTransactionAsync({
            val results = it.where(User::class.java)
                    .findAll()
            listener.onResult(realm.copyFromRealm(results))
        }, {
            listener.onSuccess()
            realm.close()
        }, {
            listener.onError(it)
            realm.close()
        })
    }
}