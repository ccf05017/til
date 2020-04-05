package com.poppo.practise

import android.app.Person
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class PhoneBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_book)

        val phoneBook = createFakePhoneBook(30)
        createPhoneBookList(phoneBook)
    }

    private fun createFakePhoneBook(fakeNumber: Int = 10, phoneBook: PhoneBook = PhoneBook()): PhoneBook {
        for (i in 0 until fakeNumber) {
            phoneBook.addPerson(
                PhoneUser("user$i", "010-0000-000$i")
            )
        }
        return phoneBook
    }

    private fun createPhoneBookList(phoneBook: PhoneBook) {
        val layoutInflater = LayoutInflater.from(this@PhoneBookActivity)
        val container = findViewById<LinearLayout>(R.id.phonebook_container)
        for (i in 0 until phoneBook.userList.size) {
            val view = layoutInflater.inflate(R.layout.phone_book_item, null)
            val personNameView = view.findViewById<TextView>(R.id.person_name)
            personNameView.text = phoneBook.userList[i].name
            addSetOnClickListener(phoneBook.userList[i], view)
            container.addView(view)
        }
    }

    private fun addSetOnClickListener(person: PhoneUser, view: View) {
        view.setOnClickListener {
            val intent = Intent(this@PhoneBookActivity, PhoneBookDetail::class.java)
            intent.putExtra("name", person.name)
            intent.putExtra("number", person.phoneNumber)
            startActivity(intent)
        }
    }
}

class PhoneBook() {
    val userList = ArrayList<PhoneUser>()

    fun addPerson(user: PhoneUser) {
        userList.add(user)
    }
}

class PhoneUser(val name: String, val phoneNumber: String) {

}
