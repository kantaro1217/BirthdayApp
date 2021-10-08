package jp.kantaro1217.birthdayapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import io.realm.Realm
import kotlinx.android.synthetic.main.content_input.*

class InputActivity : AppCompatActivity() {

    private var mBirthday: BirthdayPerson? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // ActionBarを設定する
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        // UI Button設定
        done_button.setOnClickListener{
            addBirthday()
            finish()
        }

        // EXTRA_PERSONからidを取得して、idからBirthdayのインスタンスを取得
        val intent = intent
        val BirthdayId = intent.getIntExtra(EXTRA_PERSON, -1)
        val realm = Realm.getDefaultInstance()
        mBirthday = realm.where(BirthdayPerson::class.java).equalTo("id", BirthdayId).findFirst()
        realm.close()
    }

    private fun addBirthday() {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()

        mBirthday = BirthdayPerson()
        val birthdayRealmResults = realm.where(BirthdayPerson::class.java).findAll()

        val identifier: Int =
            if(birthdayRealmResults.max("id") != null) {
                birthdayRealmResults.max("id")!!.toInt() + 1
            } else {
                0
            }

        mBirthday!!.id = identifier

        val month: String = month_edit_text.text.toString()
        val day: String = day_edit_text.text.toString()
        val name = name_edit_text.text.toString()

        mBirthday!!.month = month.toIntOrNull()!!
        mBirthday!!.day = day.toIntOrNull()!!
        mBirthday!!.name = name
        mBirthday!!.isEnabled = true

        realm.copyToRealmOrUpdate(mBirthday!!)
        realm.commitTransaction()

        realm.close()
    }
}