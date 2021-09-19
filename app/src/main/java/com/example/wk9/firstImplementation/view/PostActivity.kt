package com.example.wk9.firstImplementation.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.R
import com.example.wk9.firstImplementation.model.adapter.PostRecyclerViewAdapter
import com.example.wk9.databinding.ActivityMainBinding
import com.example.wk9.firstImplementation.model.OnClickPostItem
import com.example.wk9.firstImplementation.model.Post
import com.example.wk9.firstImplementation.model.repository.Repository
import com.example.wk9.firstImplementation.model.utils.Constants.Companion.TAG
import com.example.wk9.firstImplementation.viewModel.MainActivityViewModel
import com.example.wk9.firstImplementation.viewModel.MainActivityViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class PostActivity : AppCompatActivity(), AddPostDialog.UploadDialogListener, OnClickPostItem {
    private lateinit var binding: ActivityMainBinding
   private lateinit var recyclerView: RecyclerView
   private lateinit var recyclerViewAdapter: PostRecyclerViewAdapter
    private lateinit var arrayListOfPost: ArrayList<Post>
   private lateinit var copyOfArrayListOfPost: ArrayList<Post>
    private val repository = Repository()
    private val viewModelFactory = MainActivityViewModelFactory(repository)
    private val viewModel by lazy { ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)


        supportActionBar?.title="FaceBook Blog (MVVM)"
        supportActionBar?.setLogo(Drawable.createFromPath("fb_logo_30x50"))

        arrayListOfPost = arrayListOf()

        copyOfArrayListOfPost = arrayListOf()

        val addPostFab = binding.fabPost
        addPostFab.setOnClickListener {
            val fragment = AddPostDialog(arrayListOfPost, this)
            fragment.show(supportFragmentManager,"addPostDialogFragment")

        }

        recyclerViewAdapter = PostRecyclerViewAdapter(this,arrayListOfPost)

        //initialize recyclerview
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this@PostActivity)
        recyclerView.adapter = recyclerViewAdapter

        viewModel.getPost()

        viewModel.myResponse.observe(this, Observer { response ->
            if (response != null){

                //add post to recyclerView
                recyclerViewAdapter.addPost(response)
                copyOfArrayListOfPost.addAll(arrayListOfPost)
            }else{
              Toast.makeText(this, "No result to Display",Toast.LENGTH_LONG).show()
                Log.d(TAG, "no result found")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        val item = menu!!.findItem(R.id.search_bar)
        if (item != null){
            var searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        arrayListOfPost.clear()
                        var search = newText.lowercase(Locale.getDefault())
                        for (word in copyOfArrayListOfPost) {
                            if (word.body.lowercase(Locale.getDefault()).contains(search)) {
                                arrayListOfPost.add(word)

                            }
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }else{
                        arrayListOfPost.clear()
                        arrayListOfPost.addAll(copyOfArrayListOfPost)
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

//
    override fun sendPost(post: Post) {
        Log.d("KKK", "FROM ALERT DIALOG:$post ")
        arrayListOfPost.add(post)
        copyOfArrayListOfPost.add(post)
        recyclerViewAdapter.notifyDataSetChanged()

        viewModel.pushPostToNetwork(post)
        viewModel.pushPushResponse.observe(this,{
             val response = it.body()
            Log.d("KKK", "OUR RESPONSE: $response")
            if (it.isSuccessful){
                Toast.makeText(this,"Post successfully added to Network",Toast.LENGTH_LONG).show()
//                arrayListOfPost.add(post)
//                recyclerViewAdapter.notifyDataSetChanged()
            }
        })

    }

    override fun passToCommentActivity(position: Int, id: Int, body: String) {
        val intent = Intent(this,CommentActivity::class.java).apply {
            putExtra("id",id)
            putExtra("body",body)
        }
        startActivity(intent)
    }

//    fun searchQuerry(){
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                arrayListOfPost.clear()
//                val typedText = newText?.lowercase()?.trim().toString()
//                if (typedText.isNotEmpty()){
//                    copyOfArrayListOfPost.forEach {
//                        if (it.body.contains(typedText,true)){
//                            arrayListOfPost.add(it)
//                            recyclerViewAdapter.notifyDataSetChanged()
//                        }
//                    }
//                }else{
//                    arrayListOfPost.clear()
//                    arrayListOfPost.addAll(copyOfArrayListOfPost)
//                }
//                return true
//            }
//        })
//    }
}