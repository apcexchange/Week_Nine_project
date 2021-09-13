package com.example.wk9.firstImplementation.model

interface OnClickPostItem {
    fun passToCommentActivity(position: Int,id:Int,body:String)
}