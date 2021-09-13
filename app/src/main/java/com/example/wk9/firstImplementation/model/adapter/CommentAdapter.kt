package com.example.wk9.firstImplementation.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.R
import com.example.wk9.firstImplementation.model.AllComments
import com.example.wk9.firstImplementation.model.Comment

class CommentAdapter(private var arrayListOfComment: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
//    private var commentItem = arrayListOf<Comment>()

    class CommentViewHolder(view: View):RecyclerView.ViewHolder(view) {

//        private var commentID: TextView = view.findViewById(R.id.commentId)
        private var commentEmail: TextView = view.findViewById(R.id.commentEmail)
        private var commentBody: TextView = view.findViewById(R.id.commentBody)
        private var commentName: TextView = view.findViewById(R.id.commentName)

        fun commentBindView(comment:Comment){
//            commentID.text = comment.id.toString()

            commentName.text = comment.name
            commentBody.text = comment.body
            commentEmail.text = comment.email
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item,parent,false)
        return CommentViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.commentBindView(arrayListOfComment[position])

    }

    override fun getItemCount(): Int {
        return arrayListOfComment.size
    }


    fun addComment(allComment: AllComments){
//        this.commentItem = allComment as ArrayList<Comment>
        arrayListOfComment.addAll(allComment)
        notifyDataSetChanged()

    }
}