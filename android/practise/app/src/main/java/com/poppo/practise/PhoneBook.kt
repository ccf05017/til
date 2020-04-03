package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_view_prac.view.*

class PhoneBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_book)

        val userList = listOf(
            PhoneUser("user1"),
            PhoneUser("user2"),
            PhoneUser("user3"),
            PhoneUser("user4"),
            PhoneUser("user5")
        )

        val container = findViewById<LinearLayout>(R.id.phonebook_container)
        val inflator = LayoutInflater.from(this@PhoneBook)

        for (i in userList.indices) {
            val userView = inflator.inflate(R.layout.phone_book_item, null)
            val header = userView.findViewById<TextView>(R.id.phonebook_header)
            val contentHeader = userView.findViewById<TextView>(R.id.phonebook_content_header)
            val content = userView.findViewById<TextView>(R.id.phonebook_content)

            header.text = userList[i].name.substring(0,1)
            contentHeader.text = userList[i].name.substring(0,1)
            content.text = userList[i].name

            container.addView(userView)
        }
    }
}

class PhoneUser(val name: String) {

}
