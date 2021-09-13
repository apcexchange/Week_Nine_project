package com.example.wk9.firstImplementation.model.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.firstImplementation.model.adapter.CommentAdapter
import com.example.wk9.databinding.ActivityCommentBinding
import com.example.wk9.firstImplementation.model.Comment
import com.example.wk9.firstImplementation.model.repository.Repository
import com.example.wk9.firstImplementation.model.viewModel.MainActivityViewModel
import com.example.wk9.firstImplementation.model.viewModel.MainActivityViewModelFactory

class CommentActivity : AppCompatActivity(),AddCommentDialog.UploadCommentInterface {

   private lateinit var binding: ActivityCommentBinding
    private lateinit var commentRV: RecyclerView
    private lateinit var commentRvAdapter: CommentAdapter
    private lateinit var arrayListOfComment: ArrayList<Comment>

    private val repository = Repository()
    private val viewModelFactory = MainActivityViewModelFactory(repository)
    private val viewModel by lazy { ViewModelProvider(this,viewModelFactory).get(
        MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // receive data from post Activity

        val postId = intent.getIntExtra("id", 1)
        val postBody = intent.getStringExtra("body")

        binding.postBody.text = postBody

        arrayListOfComment = arrayListOf()

        binding.fabComment.setOnClickListener {
            val fragment = AddCommentDialog(arrayListOfComment,this)
            fragment.show(supportFragmentManager,"addCommentDialogFragment")
        }

        commentRvAdapter = CommentAdapter(arrayListOfComment)

        //initialize RV
        commentRV = binding.commentRecyclerview
        commentRV.layoutManager = LinearLayoutManager(this)
        commentRV.adapter = commentRvAdapter



        viewModel.getEachPostComment(postId)

        viewModel.commentResponse.observe(this, Observer {
        val comment = it.body()
            if (comment != null){
                commentRvAdapter.addComment(comment)
            }
        })
    }

    override fun sendComment(comment: Comment) { arrayListOfComment.add(comment)
        commentRvAdapter.notifyDataSetChanged()
        binding.initialNumberOfComment.text = "${arrayListOfComment.size}"
        Log.d("under1", "sendComment:$arrayListOfComment ")
        viewModel.pushCommentToNetwork(comment)
        viewModel.pushCommentResponse.observe(this,{
            val response = it.body()
            Log.d("ADD", "sendComment: $response")
            if (it.isSuccessful){
                Toast.makeText(this,"comment successfully added to Api",Toast.LENGTH_LONG).show()
//                arrayListOfComment.add(comment)
                Log.d("under", "sendComment: $arrayListOfComment")
//                commentRvAdapter.notifyDataSetChanged()
            }
        })
    }
}