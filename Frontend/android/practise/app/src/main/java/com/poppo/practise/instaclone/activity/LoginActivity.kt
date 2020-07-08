package com.poppo.practise.instaclone.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.poppo.practise.R
import com.poppo.practise.instaclone.dto.LoginResponseDto
import com.poppo.practise.instaclone.utils.MasterApplication
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var usernameView: EditText
    lateinit var userPasswordView: EditText
    lateinit var registerBtn: TextView
    lateinit var loginBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews(this@LoginActivity)
        setupListeners(this@LoginActivity)
    }

    private fun initViews(activity: Activity) {
        registerBtn = activity.findViewById(R.id.register)
        loginBtn = activity.findViewById(R.id.login)
        usernameView = activity.findViewById(R.id.username_inputbox)
        userPasswordView = activity.findViewById(R.id.password_inputbox)
    }

    private fun setupListeners(activity: Activity) {
        registerBtn.setOnClickListener {
            register(this@LoginActivity)
        }
        loginBtn.setOnClickListener {
            login(this@LoginActivity)
        }
    }

    private fun register(activity: Activity) {
        val intent = Intent(activity, EmailSignupActivity::class.java)
        startActivity(intent)
    }

    private fun login(activity: Activity) {
        val username = getUserName()
        val password = getPassword()
        (application as MasterApplication).service.login(
            username, password
        ).enqueue(object: Callback<LoginResponseDto> {
            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    val token = body!!.token!!
                    saveUserToken(token, activity)

                    (application as MasterApplication).createRetrofit()

                    Toast.makeText(activity, "로그인 하셨습니다", Toast.LENGTH_LONG).show()

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

    private fun getUserName(): String {
        return usernameView.text.toString()
    }

    private fun getPassword(): String {
        return userPasswordView.text.toString()
    }
}
