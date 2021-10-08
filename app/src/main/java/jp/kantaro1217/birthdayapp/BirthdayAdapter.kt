package jp.kantaro1217.birthdayapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class BirthdayAdapter(context: Context): BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    var mBirthdayList= mutableListOf<BirthdayPerson>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return mBirthdayList.size
    }

    override fun getItem(position: Int): Any {
        return mBirthdayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null)

        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        //val textView2 = view.findViewById<TextView>(android.R.id.text2)

        var title: String = mBirthdayList[position].month.toString() + "/" + mBirthdayList[position].day.toString() + " " + mBirthdayList[position].name
        textView1.text =  title

        //val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE)
        //val date = mBirthdayList[position].date
        //textView2.text = simpleDateFormat.format(date)

        return view
    }
}