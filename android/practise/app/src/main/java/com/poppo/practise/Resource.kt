package com.poppo.practise

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_resource.*

class Resource : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        // 예전 방식
        // 안드로이드 OS 버전 차이 때문에 예전 방식도 다 알고 있어야 한다.
        // val ment = resources.getString(R.string.hello)

        val ment = getString(R.string.hello)
        Log.d("ment", ment)

        // SDK 버전에 따른 분기 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setBackgroundColor(getColor(R.color.colorPrimary))
        }
    }
}
