package com.example.wk9.secondImplementation.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.R
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPostItems

class MvcPostAdapter(
    private var listOfPosts: MutableList<MvcPostItems>,private var mvcOnclickPostItems: MvcOnclickPostItems
): RecyclerView.Adapter<MvcPostAdapter.MvcPostViewHolder>()  {

    class MvcPostViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem) {

        private var postID: TextView = viewItem.findViewById(R.id.tvId)
        private var postTitle: TextView = viewItem.findViewById(R.id.tvTitle)
        private var postBody: TextView = viewItem.findViewById(R.id.tvBody)


        fun bindMvcView(post: MvcPostItems){
            postID.text = post.id.toString()
            postTitle.text = post.title
            postBody.text = post.body

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvcPostViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent,false)
        return MvcPostViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MvcPostViewHolder, position: Int) {
        holder.bindMvcView(listOfPosts[position])
        val id = listOfPosts[position].id
        val body= listOfPosts[position].body

        holder.itemView.setOnClickListener {
            mvcOnclickPostItems.mvcPassToCommentActivity(position, id, body)
        }

        }

    override fun getItemCount(): Int {
        return listOfPosts.size
    }


    //Add posts to list
    fun addPosts(posts: List<MvcPostItems>?) {
        if (posts != null) {
            listOfPosts.addAll(posts)
            notifyDataSetChanged()
        }
    }

}