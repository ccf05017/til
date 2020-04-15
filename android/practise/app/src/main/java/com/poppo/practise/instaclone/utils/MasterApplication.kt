package com.poppo.practise.instaclone.utils

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.poppo.practise.RetrofitService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication: Application() {
    lateinit var service: RetrofitService

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this) // 반드시 사용 전에 초기화 해줘야 한다.
        createRetrofit()
    }

    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()

            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token $token")
                        .build()
                    it.proceed(request)
                }
            } else {
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    private fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("token", "null")
        if (token != "null") {
            return true
        }
        return false
    }

    private fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("token", "null")

        return token
    }
}