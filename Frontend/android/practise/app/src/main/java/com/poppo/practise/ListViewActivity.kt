package com.poppo.practise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val carList = ArrayList<CarForList>()
        for (i in 0 until 50) {
            carList.add(CarForList("car $i", "engine $i"))
        }

        val layoutInflater = LayoutInflater.from(this@ListViewActivity)
        val adapter = ListViewAdapter(carList, layoutInflater)
        list_view.adapter = adapter
        list_view.setOnItemClickListener { parent, view, position, id ->
            val carName = (adapter.getItem(position) as CarForList).name
            val carEngine = (adapter.getItem(position) as CarForList).engine

            Toast.makeText(
                this@ListViewActivity,
                "$carName $carEngine",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class ListViewAdapter(
    private val carForList: ArrayList<CarForList>,
    private val layoutInflater: LayoutInflater
): BaseAdapter() {
    // 실제 뷰를 그리는 부분
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.item_view, null)
            holder = ViewHolder()

            holder.carName = view.findViewById(R.id.car_name)
            holder.carEngine = view.findViewById(R.id.car_engine)

            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }
        holder.carName?.text = carForList[position].name
        holder.carEngine?.text = carForList[position].engine

        return view
    }

    override fun getItem(position: Int): Any {
        return carForList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // 전체 갯수를 관장하는 함수
    override fun getCount(): Int {
        return carForList.size
    }
}

class ViewHolder {
    var carName: TextView? = null
    var carEngine: TextView? = null
}