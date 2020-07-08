package com.poppo.practise

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_go_to_naver.*

class GoToNaver : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_to_naver)

        moveButton.setOnClickListener {
            val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(textInput.text.toString()))
            startActivity(moveIntent)
        }

        textInput.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Log.d("edit" , "afterTextChanged: $s")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("edit" , "beforeTextChanged: $start $count $after")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("edit" , "onTextChanged: $start $before $count")
            }
        })
    }
}
