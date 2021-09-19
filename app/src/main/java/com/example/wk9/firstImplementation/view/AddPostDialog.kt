package com.example.wk9.firstImplementation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wk9.databinding.FragmentAddPostDialogBinding
import com.example.wk9.firstImplementation.model.Post
import java.lang.ClassCastException

class AddPostDialog(private val arrayListOfPosts: ArrayList<Post>, private val listener: UploadDialogListener) : DialogFragment() {
 private var _binding: FragmentAddPostDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPostDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btPost.setOnClickListener {
            val postTitle = binding.newPostTitle.text.toString()
            val postBody = binding.newPostBody.text.toString()
            val userId = 1
            val id = arrayListOfPosts.size+1

            when{
                postTitle.isEmpty() -> {
                    binding.newPostTitle.error = "Title can't be empty"
                    return@setOnClickListener
                }

                postBody.isEmpty() -> {
                    binding.newPostBody.error = "Body can't be empty"
                    return@setOnClickListener
                }
                else -> {
                    val newPost = Post(userId,id,postTitle,postBody)
                   listener.sendPost(newPost)
                    dismiss()
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement UploadDialogListener")
        }
    }

    interface UploadDialogListener{
        fun sendPost(post:Post)
    }

}