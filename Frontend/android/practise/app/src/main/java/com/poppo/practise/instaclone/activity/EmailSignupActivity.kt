package com.poppo.practise.instaclone.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.poppo.practise.R
import com.poppo.practise.instaclone.dto.RegisterRequestDto
import com.poppo.practise.instaclone.dto.RegisterResponseDto
import com.poppo.practise.instaclone.utils.MasterApplication
import kotlinx.android.synthetic.main.activity_email_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignupActivity : AppCompatActivity() {

    lateinit var usernameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText
    lateinit var registerBtn: TextView
    lateinit var loginBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_signup)

        initView(this@EmailSignupActivity)
        setupListener(this@EmailSignupActivity)
    }

    private fun setupListener(activity: Activity) {
        registerBtn.setOnClickListener {
            register(this@EmailSignupActivity)
        }
        loginBtn.setOnClickListener {
            startActivity(
                Intent(this@EmailSignupActivity, LoginActivity::class.java)
            )
        }
    }

    private fun register(activity: Activity) {
        val username = getUserName()
        val password1 = getUserPassword1()
        val password2 = getUserPassword2()

        (application as MasterApplication).service.register(
            username, password1, password2
        ).enqueue(object: Callback<RegisterResponseDto> {
            override fun onFailure(call: Call<RegisterResponseDto>, t: Throwable) {
                Toast.makeText(activity, "가입에 실패했습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<RegisterResponseDto>,
                response: Response<RegisterResponseDto>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "가입에 성공했습니다.", Toast.LENGTH_LONG).show()
                    val user = response.body()
                    val token = user!!.token!!

                    saveUserToken(token, activity)

                    (application as MasterApplication).createRetrofit()
                    activity.startActivity(
                        Intent(activity, PostListActivity::class.java)
                    )
                }
            }
        })
    }

    private fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("token", token)
        editor.commit()
    }

    private fun initView(activity: Activity) {
        usernameView = activity.findViewById(R.id.username_inputbox)
        userPassword1View = activity.findViewById(R.id.password1_inputbox)
        userPassword2View = activity.findViewById(R.id.password2_inputbox)
        registerBtn = activity.findViewById(R.id.register)
        loginBtn = activity.findViewById(R.id.login)
    }

    fun getUserName(): String {
        return usernameView.text.toString()
    }

    fun getUserPassword1(): String {
        return userPassword1View.text.toString()
    }

    fun getUserPassword2(): String {
        return userPassword2View.text.toString()
    }
}
