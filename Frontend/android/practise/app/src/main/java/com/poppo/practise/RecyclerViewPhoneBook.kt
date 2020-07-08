package com.poppo.practise

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view_phone_book.*

class RecyclerViewPhoneBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_phone_book)

//        val phoneBookRecyclerAdapter = PhoneBookRecyclerAdapter(
//            phoneBookList = createFakePhoneBook(fakeNumber = 30),
//            inflater = LayoutInflater.from(this@RecyclerViewPhoneBook),
//            activity = this
//        )
//        phonebook_recycler_view.adapter = phoneBookRecyclerAdapter
//        phonebook_recycler_view.layoutManager = LinearLayoutManager(this@RecyclerViewPhoneBook)

        // 위와 완전히 동일한 코드
        with(phonebook_recycler_view) {
            this.adapter = PhoneBookRecyclerAdapter(
                phoneBookList = createFakePhoneBook(fakeNumber = 30),
                inflater = LayoutInflater.from(this@RecyclerViewPhoneBook),
                activity = this@RecyclerViewPhoneBook
            )
            this.layoutManager = LinearLayoutManager(this@RecyclerViewPhoneBook)
        }
    }

    fun createFakePhoneBook(fakeNumber: Int = 10, phoneBook: PhoneBook = PhoneBook()): PhoneBook {
        for (i in 0 until fakeNumber) {
            phoneBook.addPerson(
                PhoneUser("user$i", "010-0000-000$i")
            )
        }
        return phoneBook
    }
}

class PhoneBookRecyclerAdapter(
    val phoneBookList: PhoneBook,
    val inflater: LayoutInflater,
    val activity: Activity
): RecyclerView.Adapter<PhoneBookRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val personName: TextView
        init {
            personName = itemView.findViewById(R.id.person_name)
            itemView.setOnClickListener {
                val intent = Intent(activity, PhoneBookDetail::class.java)
                intent.putExtra("name", phoneBookList.userList[adapterPosition].name)
                intent.putExtra("number", phoneBookList.userList[adapterPosition].phoneNumber)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.phone_book_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return phoneBookList.userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.personName.text = phoneBookList.userList[position].name
    }
}
