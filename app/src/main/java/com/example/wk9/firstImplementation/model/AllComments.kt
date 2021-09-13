package com.example.wk9.firstImplementation.model



class AllComments: ArrayList<Comment>()


data class Comment(
    var postId: Int,
    var id: Int,
    var name: String,
    var email: String,
    var body: String
)