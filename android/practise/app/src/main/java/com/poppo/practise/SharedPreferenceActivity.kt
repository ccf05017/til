package com.poppo.practise

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_shared_preference.*

class SharedPreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        save_btn.setOnClickListener {
            val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreference.edit()

            editor.putString("hello", "world")
            editor.putString("goodbye", "world2")
            editor.commit()
        }

        delete_button.setOnClickListener {
            val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreference.edit()

            editor.remove("hello")
            editor.commit()
        }

        delete_all_button.setOnClickListener {
            val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreference.edit()

            editor.clear()
            editor.commit()
        }

        load_btn.setOnClickListener {
            val sp = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val value1 = sp.getString("hello", "None1")
            val value2 = sp.getString("goodbye", "None2")

            Log.d("sp-test: ", value1)
            Log.d("sp-test: ", value2)
        }
    }
}
