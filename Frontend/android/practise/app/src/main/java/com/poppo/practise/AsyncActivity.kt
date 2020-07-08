package com.poppo.practise

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_async.*
import java.lang.Exception

class AsyncActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)

        var task: BackgroundAsyncTask? = null

        start_btn.setOnClickListener {
            task = BackgroundAsyncTask(progress_bar, ment)
            task?.execute()
        }

        stop_btn.setOnClickListener {
            task?.cancel(true)
        }
    }
}

class BackgroundAsyncTask(
    private val progressBar: ProgressBar,
    private val progressText: TextView
): AsyncTask<Int, Int, Int>() {
    private var percent: Int = 0

    override fun onPreExecute() {
        percent = 0
        progressBar.progress = percent
    }

    override fun doInBackground(vararg params: Int?): Int {
        while (!isCancelled()) {
            percent++
            if (percent > 100) {
                break
            } else {
                publishProgress(percent)
            }
            try {
                Thread.sleep(100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return percent
    }

    override fun onProgressUpdate(vararg values: Int?) {
        progressBar.progress = values[0] ?: 0
        progressText.text = "진행률: " + values[0]
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Int?) {
        progressText.text = "작업이 완료됐습니다."
    }

    override fun onCancelled(result: Int?) {
        progressBar.progress = 0
        progressText.text = "작업이 취소됐습니다."
    }
}
