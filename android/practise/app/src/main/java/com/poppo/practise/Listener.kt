package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_listener.*

class Listener : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listener)

        // 1-1. 직접 가져오는 방법(Java 시절 쓰던 방법)
        // val textView : TextView = findViewById(R.id.hello)

        // 1-2. 다른 라이브러리처럼 import 하는 방법
        // hello

        // 2-1. 람다를 통한 이벤트 리스너 작성
        hello.setOnClickListener {
            Log.d("click", "Click!!")
        }

        // 2-2. 무명함수를 통한 이벤트 리스너 작성 (알아서 람다로 바꾸라고 권장한다)
        hello.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("click", "Click!!")
            }
        })

        // 2-3. 함수를 통한 이벤트 리스너 작성
        val click = object: View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("click", "Click!!")
            }
        }
        hello.setOnClickListener(click)
    }
}
