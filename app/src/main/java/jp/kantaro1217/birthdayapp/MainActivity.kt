package jp.kantaro1217.birthdayapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.Sort
import java.util.Date
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_PERSON = "jp.kantaro1217.birthdayapp.BirthdayPerson"

class MainActivity : AppCompatActivity() {
    private lateinit var mRealm: Realm
    private val mRealmListener = object : RealmChangeListener<Realm> {
        override fun onChange(element: Realm) {
            reloadListView()
        }
    }



    private lateinit var mBirthdayAdapter: BirthdayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        // Realmの設定
        mRealm = Realm.getDefaultInstance()
        mRealm.addChangeListener(mRealmListener)

        //ListViewの設定
        mBirthdayAdapter = BirthdayAdapter(this)

        //ListViewのクリック設定
        listView1.setOnItemClickListener{ parent, view, position, id ->
            // 詳細画面へ
            val birthday = parent.adapter.getItem(position) as BirthdayPerson
            val intent = Intent(this, BirthdayDetailActivity::class.java)
            intent.putExtra(EXTRA_PERSON, birthday.id)
            startActivity(intent)
        }


        // ListView長押しの処理
        listView1.setOnItemLongClickListener { parent, view,position, id ->
            //delete method
            val birthday = parent.adapter.getItem(position) as BirthdayPerson

            //dialog
            val builder = AlertDialog.Builder(this)

            builder.setTitle("削除")
            builder.setMessage(birthday.name + "をリストから削除しますか")

            builder.setPositiveButton("OK"){_, _ ->
                //val results = mRealm.where(BirthdayPerson::class.java).equalTo("id", birthday.id).findAll()
                birthday.isEnabled = false

                mRealm.beginTransaction()
                // 論理削除
                mRealm.copyToRealmOrUpdate(birthday!!)
                mRealm.commitTransaction()

                reloadListView()
            }

            builder.setNegativeButton("CANCEL", null)

            val dialog = builder.create()
            dialog.show()

            true
        }

        reloadListView()
    }

    //ここから
    private fun reloadListView() {
        //Realmから月日順でデータ取得
        val BirthdayRealmResults = mRealm.where(BirthdayPerson::class.java).equalTo("isEnabled", true).findAll().sort("month", Sort.ASCENDING)

        mBirthdayAdapter.mBirthdayList = mRealm.copyFromRealm(BirthdayRealmResults)

        listView1.adapter = mBirthdayAdapter

        mBirthdayAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        mRealm.close()
    }
}