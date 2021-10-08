package jp.kantaro1217.birthdayapp

import android.app.Application
import io.realm.Realm

class BirthdayApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}