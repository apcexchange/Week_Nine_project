package com.example.wk9.firstImplementation.model.remote

import com.example.wk9.firstImplementation.model.AllComments
import com.example.wk9.firstImplementation.model.Comment
import com.example.wk9.firstImplementation.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {
    @GET("posts/")
    suspend fun getPost(): ArrayList<Post>

    @POST("posts/")
    suspend fun pushPostApi(@Body post:Post) : Response<Post>


    @GET("posts/{postNumber}/comments")
    suspend fun getComments(@Path("postNumber") postNumber: Int) : Response<AllComments>

    @POST("comments")
    suspend fun pushCommentApi(@Body comment:Comment) : Response<Comment>

}