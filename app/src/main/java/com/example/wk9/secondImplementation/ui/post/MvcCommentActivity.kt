package com.example.wk9.secondImplementation.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wk9.databinding.ActivityMvcCommentBinding
import com.example.wk9.firstImplementation.view.AddCommentDialog
import com.example.wk9.secondImplementation.model.adapters.MvcCommentAdapter
import com.example.wk9.secondImplementation.model.api.MvcRetrofit
import com.example.wk9.secondImplementation.model.data.mvcComment.MvcCommentItems
import com.example.wk9.secondImplementation.model.data.mvcComment.MvcComments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class MvcCommentActivity : AppCompatActivity(),MvcAddCommentDialog.MvcUploadCommentInterface {

    private lateinit var binding: ActivityMvcCommentBinding
    private lateinit var listOfComment: ArrayList<MvcCommentItems>
    private lateinit var recyclerView: RecyclerView
    private lateinit var commentRecyclerView: MvcCommentAdapter
    private var postId by Delegates.notNull<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // receive data from post Activity

        postId = intent.getIntExtra("id", 1)
        val postBody = intent.getStringExtra("body")

        binding.mvcPostBody.text = postBody
        initializeRecyclerView()
        mvcGEtComment()

        binding.fabComment.setOnClickListener {
            val fragment = MvcAddCommentDialog(listOfComment, this)
            fragment.show(supportFragmentManager, "MvcaddCommentDialogFragment")



        }
    }

        private fun initializeRecyclerView() {
            listOfComment = arrayListOf()
            commentRecyclerView = MvcCommentAdapter(listOfComment)
            recyclerView = binding.commentRecyclerview
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = commentRecyclerView

        }

        private fun mvcGEtComment() {
            val getCommentRetrofit = MvcRetrofit.api.getComments(postId)
            getCommentRetrofit.enqueue(object : Callback<MvcComments?> {
                override fun onResponse(
                    call: Call<MvcComments?>,
                    response: Response<MvcComments?>
                ) {
                    val fetchedComment = response.body()
                    if (fetchedComment != null) {
                        commentRecyclerView.addComment(fetchedComment)
                    }
                }

                override fun onFailure(call: Call<MvcComments?>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.message}")
                }
            })
        }

    override fun sendComment(comment: MvcCommentItems) {
        commentRecyclerView.notifyDataSetChanged()
        binding.initialNumberOfComment.text = "${listOfComment.size}"
        Log.d("Added comment", "sendComment: $listOfComment")
        val sendCommentRetrofit = MvcRetrofit.api.addComment(comment)
        sendCommentRetrofit.enqueue(object : Callback<MvcCommentItems?> {
            override fun onResponse(
                call: Call<MvcCommentItems?>,
                response: Response<MvcCommentItems?>
            ) {
                if (response.isSuccessful){
                    val sentComment = response.body()
                    commentRecyclerView.addNewComment(comment)
                    Log.d("ADDNEWCOMMENT", "onResponse: $sentComment")
                    Toast.makeText(this@MvcCommentActivity,"comment added sucessefully",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MvcCommentActivity,"something went wrong",Toast.LENGTH_SHORT).show()
                    Log.d("kkk", "onResponse: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<MvcCommentItems?>, t: Throwable) {
                Log.d("KKK", "onFailure: ${t.message}")
                Toast.makeText(this@MvcCommentActivity,"Check your Network", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
