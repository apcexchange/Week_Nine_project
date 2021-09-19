package com.example.wk9.secondImplementation.ui.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.R
import com.example.wk9.databinding.ActivityMvcPostBinding
import com.example.wk9.secondImplementation.model.adapters.MvcOnclickPostItems
import com.example.wk9.secondImplementation.model.adapters.MvcPostAdapter
import com.example.wk9.secondImplementation.model.api.MvcRetrofit
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPostItems
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPosts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MvcPostActivity : AppCompatActivity(), MvcOnclickPostItems, MvcAddPostDialog.MvcUploadDialogListener {

    private lateinit var binding: ActivityMvcPostBinding
    private lateinit var postAdapter: MvcPostAdapter
    private lateinit var recyclerView: RecyclerView
    private var linearLayoutManager = LinearLayoutManager(this)
    private lateinit var listOfPost: MutableList<MvcPostItems>
    private lateinit var copyOfListOfPost: MutableList<MvcPostItems>
    private lateinit var commentResponse: MvcPosts
    private var newPostBody: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="FaceBook Blog (MVC)"
        setUpRecyclerView()
        mvcGetPost()
        mvcAddPostToNetwork()
        mvcSearchPost()


        binding.mvcFabPost.setOnClickListener {
            mvcAddPost()
        }
    }

    // set up recycler view
        private fun setUpRecyclerView(){
             listOfPost = mutableListOf()
            copyOfListOfPost = mutableListOf()
            postAdapter = MvcPostAdapter(listOfPost,this)
            recyclerView = binding.mvcRecyclerview
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = postAdapter
        }

    // function to get post and display on ui
    private fun mvcGetPost() {
        val connectedRetrofit = MvcRetrofit.api.getPost()
        connectedRetrofit.enqueue(object : Callback<MvcPosts?> {
            override fun onResponse(call: Call<MvcPosts?>, response: Response<MvcPosts?>) {
                commentResponse = response.body()!!
                if (response.isSuccessful){
                    postAdapter.addPosts(commentResponse)
                }else{
                    Toast.makeText(this@MvcPostActivity,"something went wrong",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MvcPosts?>, t: Throwable) {
                Log.d("MvcGetPost", "onFailure: ${t.message}")
            }
        })
    }


    //function to add post
    private fun mvcAddPostToNetwork(){
        val id = listOfPost.size +1
        val postItems =newPostBody?.let { MvcPostItems(it,id,"Peter Post",11) }

        if (newPostBody?.isNotEmpty()==true){
            postItems?.let { MvcRetrofit.api.makePost(it) }
                ?.enqueue(object : Callback<MvcPostItems?> {
                    override fun onResponse(
                        call: Call<MvcPostItems?>,
                        response: Response<MvcPostItems?>
                    ) {
                        if (response.isSuccessful){
                            newPostBody?.let { mvcSendPost(postItems) }
                            Toast.makeText(this@MvcPostActivity, "Post sent successfully.", Toast.LENGTH_LONG).show()

                        }else{
                            Log.d("TAG", "sendPostToServer: ${response.code()}")
                            Toast.makeText(this@MvcPostActivity, "Check your internet and try again.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<MvcPostItems?>, t: Throwable) {
                        Log.d("TAG", "sendPostToServer: ${t.message}")
                        Toast.makeText(this@MvcPostActivity, "Check your internet and try again.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun mvcAddPost(){
        val mvcAddPostFab = binding.mvcFabPost
        mvcAddPostFab.setOnClickListener {
            val mvcFragment = MvcAddPostDialog(listOfPost,this)
            mvcFragment.show(supportFragmentManager,"mvcFragmentAddPost")
        }
    }

    // pass post body to next activity
    override fun mvcPassToCommentActivity(position: Int, id: Int, body: String) {
        val intent = Intent(this,MvcCommentActivity::class.java).apply {
            putExtra("id",id)
            putExtra("body",body)
        }
        startActivity(intent)
    }

    // search post fun
    private fun mvcSearchPost(){
        binding.mvcSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listOfPost.clear()
                val typedText = newText?.lowercase()?.trim()
                if (typedText != null && typedText.isNotEmpty()){
                    copyOfListOfPost.forEach {
                        if (it.body.contains(typedText,true)){
                            listOfPost.add(it)
                            postAdapter.notifyDataSetChanged()
                        }
                    }
                }else{
                    listOfPost.clear()
                    listOfPost.addAll(copyOfListOfPost)
                }
                return true
            }
        })
    }

    override fun mvcSendPost(posts: MvcPostItems) {
        Log.d("SendPost", "mvcSendPost: $posts")
        listOfPost.add(posts)
        copyOfListOfPost.add(posts)
        postAdapter.notifyDataSetChanged()

    }
}