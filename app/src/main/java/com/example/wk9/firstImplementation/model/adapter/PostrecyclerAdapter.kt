package com.example.wk9.firstImplementation.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.R
import com.example.wk9.firstImplementation.model.OnClickPostItem
import com.example.wk9.firstImplementation.model.Post

class PostRecyclerViewAdapter(private val onClickPostItem: OnClickPostItem,private var item:ArrayList<Post>): RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolder>() {

    inner class PostViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var postID: TextView = view.findViewById(R.id.tvId)
        private var postTitle: TextView = view.findViewById(R.id.tvTitle)
        private var postBody: TextView = view.findViewById(R.id.tvBody)


        fun bindView(post:Post){
            postID.text = post.id.toString()
            postTitle.text = post.title
            postBody.text = post.body

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent,false)
        return PostViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.bindView(item[position])
        val id = item[position].id
        val body= item[position].body
       holder.itemView.setOnClickListener {
            onClickPostItem.passToCommentActivity(position,id,body)
        }

    }


    override fun getItemCount(): Int {
        return item.size
    }

    fun addPost(allPost: List<Post>){
        item.addAll(allPost)
        notifyDataSetChanged()
    }

}