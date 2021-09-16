package com.example.wk9.secondImplementation.model.api

import com.example.wk9.secondImplementation.model.data.mvcComment.MvcComments
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPostItems
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPosts
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MvcNetworkInterface {

    //get post
    @GET("posts")
    fun getPost(): Call<MvcPosts>

    //make post request
    @POST("posts")
    fun makePost(@Body posts: MvcPostItems): Call<MvcPostItems>

    //get comment request
    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Call<MvcComments>
}