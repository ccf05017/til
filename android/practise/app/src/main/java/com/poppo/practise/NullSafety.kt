package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NullSafety : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null_safety)

        val number: Int = 10
        // val cantNullNumber: Int = null
        val canNullNumber: Int? = null
    }

    fun plus (a: Int, b: Int?): Int? {
        // 하나라도 null이면 계산값은 null이 되야 한다.
        return b?.plus(a)
    }

    fun elvis(input: Int?) {
        // 엘비스 연산자
        // null일 경우 엘비스 연산자 이후의 값을 기본값으로 설정
        val elvisResult = input ?: 10
    }
}
