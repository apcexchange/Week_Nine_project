package com.example.wk9.firstImplementation.model.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wk9.firstImplementation.model.AllComments
import com.example.wk9.firstImplementation.model.Comment
import com.example.wk9.firstImplementation.model.Post
import com.example.wk9.firstImplementation.model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivityViewModel(private val repository: Repository): ViewModel() {

    // the response for allPosts network call is stored in this Live Data

    private val _myResponse: MutableLiveData<ArrayList<Post>> = MutableLiveData()
    private var _myResponseQuerryList: ArrayList<Post>? = null
    val myResponse: LiveData<ArrayList<Post>> get() =_myResponse

    //livedata for comments
    private val _commentResponse: MutableLiveData<Response<AllComments>> = MutableLiveData()
    val commentResponse: LiveData<Response<AllComments>> get() = _commentResponse

    //live data for pushing post to network
    private val _pushPostResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    val pushPushResponse: LiveData<Response<Post>> get() = _pushPostResponse

    //livedata for pushing comment to network
    private val _pushCommentResponse: MutableLiveData<Response<Comment>> = MutableLiveData()
    val pushCommentResponse: LiveData<Response<Comment>> get() = _pushCommentResponse

    // this function function triggers the network call for Allposts
    fun getPost(){
        viewModelScope.launch {
            try {
                val response = repository.getPost()
                _myResponse.postValue(response)
                _myResponseQuerryList = response
            }catch (e: Exception){
                Log.d("getPost", "getPost: $e")
            }
        }
    }


    //function to add post
    fun pushPostToNetwork(post: Post){
        viewModelScope.launch {
            try {
                val response = repository.pushPost(post)
                _pushPostResponse.postValue(response)

                Log.d("KKK", "FROM VIEWMODEL: $response")
//                response.body()?.let { _myResponse.value?.add(it) }
            }catch (e:Exception){
                _myResponse.value=null
            }
        }
    }

    //function to trigger get comments
   fun getEachPostComment(idNum:Int){
       viewModelScope.launch {
           try {
               val commentResponse = repository.getComment(idNum)
               _commentResponse.postValue(commentResponse)
           }catch (e: Exception){
               Log.d("getComment", "getComment: $e")
           }
       }
   }

    //function to add comment
    fun pushCommentToNetwork(comment: Comment){
        viewModelScope.launch {
            try {
                val response = repository.pushComment(comment)
                _pushCommentResponse.postValue(response)
            }catch (e:Exception){
                _commentResponse.value = null
                Log.d("pushComment", "pushCommentToNetwork: $e")
            }
        }
    }

}