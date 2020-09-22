package com.poppo.practise

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_intent.*

class IntentExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        change_activity.setOnClickListener {
//            val moveIntent = Intent(this@IntentExample, IntentExample2::class.java)
//            moveIntent.putExtra("number1", 1)
//            moveIntent.putExtra("number2", 2)
//            startActivity(moveIntent)

//            val moveIntent2 = Intent(this@IntentExample, IntentExample2::class.java)
//            moveIntent2.apply {
//                this.putExtra("number1", 1)
//                this.putExtra("number2", 2)
//            }
//            startActivityForResult(moveIntent2, 3000)

            val moveBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"))
            startActivity(moveBrowserIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 3000) {
            Log.d("requestCode", requestCode.toString())
            Log.d("resultCode", resultCode.toString())
            val result = data?.getIntExtra("result", 0)
            Log.d("result", result.toString())
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
