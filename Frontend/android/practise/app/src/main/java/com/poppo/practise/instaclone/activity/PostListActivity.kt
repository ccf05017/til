package com.poppo.practise.instaclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.poppo.practise.R
import com.poppo.practise.instaclone.dto.PostResponseDto
import com.poppo.practise.instaclone.utils.MasterApplication
import kotlinx.android.synthetic.main.activity_post_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostListActivity : AppCompatActivity() {

    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        glide = Glide.with(this)

        (application as MasterApplication).service.getAllPosts().enqueue(
            object: Callback<ArrayList<PostResponseDto>> {
                override fun onFailure(call: Call<ArrayList<PostResponseDto>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ArrayList<PostResponseDto>>,
                    response: Response<ArrayList<PostResponseDto>>
                ) {
                    if (response.isSuccessful) {
                        val postList = response.body()
                        val adapter = PostAdapter(
                            postList!!,
                            LayoutInflater.from(this@PostListActivity),
                            glide
                        )
                        post_recycler_view.adapter = adapter
                        post_recycler_view.layoutManager = LinearLayoutManager(this@PostListActivity)
                    }
                }
            }
        )
    }
}

class PostAdapter(
    var postList: ArrayList<PostResponseDto>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val postOwner: TextView
        val postContent: TextView
        val postImage: ImageView

        init {
            postOwner = itemView.findViewById(R.id.post_owner)
            postContent = itemView.findViewById(R.id.post_content)
            postImage = itemView.findViewById(R.id.post_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.post_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postOwner.text = postList[position].owner
        holder.postContent.text = postList[position].content
        glide.load(postList[position].image).into(holder.postImage)
    }
}
