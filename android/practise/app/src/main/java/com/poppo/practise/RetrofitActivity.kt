package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val baseUrl: String = "http://mellowcode.org/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitService::class.java)

        service.getStudentsList().enqueue(object: Callback<ArrayList<PersonFromServer>> {
            override fun onFailure(call: Call<ArrayList<PersonFromServer>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ArrayList<PersonFromServer>>,
                response: Response<ArrayList<PersonFromServer>>
            ) {
                if (response.isSuccessful) {
                    val personList = response.body()
                }
            }
        })

//        val params = HashMap<String, Any>()
//        params.put("name", "해쉬맵..?")
//        params.put("age", 230)
//        params.put("intro", "객체를 넣는 게 아니고?")
//
//        service.createStudent(params).enqueue(object: Callback<PersonFromServer> {
//            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onResponse(
//                call: Call<PersonFromServer>,
//                response: Response<PersonFromServer>
//            ) {
//                if (response.isSuccessful) {
//                    val person = response.body()
//                    Log.d("retrofit", "name: " + person?.name)
//                }
//            }
//        })
        val person = PersonFromServer(name = "유다시티", age = 12, intro = "가 공짜인데 더 나은듯")

        service.createStudentEasy(person).enqueue(object : Callback<PersonFromServer> {
            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PersonFromServer>,
                response: Response<PersonFromServer>
            ) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log.d("retrofit", "name: " + person?.name)
                }
            }
        })
    }
}
