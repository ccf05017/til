package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import java.util.*
import kotlin.collections.ArrayList

class AddViewActivityPrac : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_view_prac)

        val carList = listOf(
            CarForList("car1", "engine1"),
            CarForList("car2", "engine2"),
            CarForList("car3", "engine3"),
            CarForList("car4", "engine4"),
            CarForList("car5", "engine5"),
            CarForList("car6", "engine6"),
            CarForList("car7", "engine7"),
            CarForList("car8", "engine8"),
            CarForList("car9", "engine9"),
            CarForList("car10", "engine10")
        )

        val container = findViewById<LinearLayout>(R.id.addview_container)
        val inflater = LayoutInflater.from(this@AddViewActivityPrac)
        for (i in carList.indices) {
            inflater.inflate(R.layout.item_view, null)
        }
    }
}

class CarForList(val name: String, val engine: String) {

}
