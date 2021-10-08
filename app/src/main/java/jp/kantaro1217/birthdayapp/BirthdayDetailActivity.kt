package jp.kantaro1217.birthdayapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.content_edit.*
import androidx.appcompat.widget.Toolbar
import io.realm.Realm

class BirthdayDetailActivity : AppCompatActivity() {
    var mBirthday: BirthdayPerson? = null

    private val onEditClickListener = View.OnClickListener {
        //　編集画面への切替
        send_gift_text.isInvisible = true
        get_gift_text.isInvisible = true
        memo_text.isInvisible = true
        send_gift_edit_text.isVisible = true
        get_gift_edit_text.isVisible = true
        memo_edit_text.isVisible = true

        // Button 表示
        edit_button.isInvisible = true
        done_button.isVisible = true

    }

    private val onDoneClickListener = View.OnClickListener {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        mBirthday!!.sendGift = send_gift_edit_text.text.toString()
        mBirthday!!.getGift = get_gift_edit_text.text.toString()
        mBirthday!!.memo = memo_edit_text.text.toString()

        realm.copyToRealmOrUpdate(mBirthday!!)
        realm.commitTransaction()

        realm.close()

        valueSet(mBirthday!!)

        send_gift_text.isVisible = true
        get_gift_text.isVisible = true
        memo_text.isVisible = true
        send_gift_edit_text.isInvisible = true
        get_gift_edit_text.isInvisible = true
        memo_edit_text.isInvisible = true

        // Button 表示
        edit_button.isVisible = true
        done_button.isInvisible = true
    }

    private fun valueSet(mBirthday: BirthdayPerson) {
        val str = mBirthday.month.toString() + "/" + mBirthday.day.toString() + "  " + mBirthday.name
        titleName.text = str
        send_gift_text.text = mBirthday.sendGift
        get_gift_text.text = mBirthday.getGift
        memo_text.text = mBirthday.memo
        send_gift_edit_text.setText(mBirthday.sendGift)
        get_gift_edit_text.setText(mBirthday.getGift)
        memo_edit_text.setText(mBirthday.memo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday_detail)

        // ActionBarを設定する
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        edit_button.setOnClickListener(onEditClickListener)
        done_button.setOnClickListener(onDoneClickListener)

        // scroll bar setting
        send_gift_text.movementMethod = ScrollingMovementMethod()
        get_gift_text.movementMethod = ScrollingMovementMethod()
        memo_text.movementMethod = ScrollingMovementMethod()

        // Birthdayのインスタンスをintent受け取って作る
        val intent = intent
        val birthdayId = intent.getIntExtra(EXTRA_PERSON, -1)
        val realm = Realm.getDefaultInstance()

        // データを持ってきて値をセット
        mBirthday = realm.where(BirthdayPerson::class.java).equalTo("id", birthdayId).findFirst()!!
        valueSet(mBirthday!!)

    }
}