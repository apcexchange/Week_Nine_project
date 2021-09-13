package com.example.wk9.firstImplementation.model.repository

import android.util.Log
import com.example.wk9.firstImplementation.model.AllComments
import com.example.wk9.firstImplementation.model.Comment
import com.example.wk9.firstImplementation.model.Post
import com.example.wk9.firstImplementation.model.remote.RetrofitInstance
import retrofit2.Response

class Repository {

    // this will contain all the post in the API
    suspend fun getPost(): ArrayList<Post> {
        return RetrofitInstance.api.getPost()
    }

    // add new post
    suspend fun pushPost(post: Post): Response<Post> {
        Log.d("Retrofit", "$post")
        return RetrofitInstance.api.pushPostApi(post)
    }

    //get comment
    suspend fun getComment(idNumber: Int): Response<AllComments> {
        return RetrofitInstance.api.getComments(idNumber)
    }

    //add new comment
    suspend fun pushComment(comment: Comment):Response<Comment>{
        Log.d("Retrofit", "$comment")
        return RetrofitInstance.api.pushCommentApi(comment)
    }
}