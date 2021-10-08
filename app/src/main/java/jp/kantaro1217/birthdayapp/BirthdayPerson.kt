package jp.kantaro1217.birthdayapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.Date

open class BirthdayPerson: RealmObject(), Serializable {
    var name: String = ""
    var month: Int = 0
    var day: Int = 0
    var sendGift: String = ""
    var getGift: String = ""
    var memo: String = ""
    var isEnabled: Boolean = true
    var category: String = ""
    var date: Date = Date()

    @PrimaryKey
    var id: Int = 0
}