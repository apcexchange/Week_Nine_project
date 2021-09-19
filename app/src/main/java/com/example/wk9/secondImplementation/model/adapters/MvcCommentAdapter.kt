package com.example.wk9.secondImplementation.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.R
import com.example.wk9.secondImplementation.model.data.mvcComment.MvcCommentItems
import com.example.wk9.secondImplementation.model.data.mvcComment.MvcComments

class MvcCommentAdapter(private var listOfComment: ArrayList<MvcCommentItems>): RecyclerView.Adapter<MvcCommentAdapter.MvcCommentViewHolder>() {


    class MvcCommentViewHolder(view:View):RecyclerView.ViewHolder(view) {
        private var commentEmail: TextView = view.findViewById(R.id.commentEmail)
        private var commentBody: TextView = view.findViewById(R.id.commentBody)
        private var commentName: TextView = view.findViewById(R.id.commentName)

        fun commentBindView(comment: MvcCommentItems){
//            commentID.text = comment.id.toString()

            commentName.text = comment.name
            commentBody.text = comment.body
            commentEmail.text = comment.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvcCommentViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item,parent,false)
        return MvcCommentViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MvcCommentViewHolder, position: Int) {
        holder.commentBindView(listOfComment[position])
    }

    override fun getItemCount(): Int {
        return  listOfComment.size
    }


    fun addComment(allComment: MvcComments){
//        this.commentItem = allComment as ArrayList<Comment>
       listOfComment.addAll(allComment)
        notifyDataSetChanged()

    }

    fun addNewComment(newcomment: MvcCommentItems){
        listOfComment.add(newcomment)
        notifyDataSetChanged()
    }


}