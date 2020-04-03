package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_thread_practise.*

class ThreadPractise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_practise)

        val runnable: Runnable = Runnable {
            Log.d("thread-1", "thread is made")
        }

        val thread: Thread = Thread(runnable)

        button.setOnClickListener {
            thread.start()
        }

        Thread(Runnable {
            Thread.sleep(2000)
            Log.d("thread-3", "thread3 is made")
            button.setBackgroundColor(resources.getColor(R.color.red))
        }).start()
    }
}
