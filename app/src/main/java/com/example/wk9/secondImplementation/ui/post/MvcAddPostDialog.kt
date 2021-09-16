package com.example.wk9.secondImplementation.ui.post

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wk9.R
import com.example.wk9.databinding.FragmentMvcAddPostDialogBinding
import com.example.wk9.firstImplementation.model.Post
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPostItems
import com.example.wk9.secondImplementation.model.data.mvcPost.MvcPosts
import java.lang.ClassCastException

class MvcAddPostDialog(private val listOfPost: MutableList<MvcPostItems>, private val listener:MvcUploadDialogListener) : DialogFragment() {
    private var _binding: FragmentMvcAddPostDialogBinding?  = null
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
        _binding = FragmentMvcAddPostDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMvcPost.setOnClickListener {
            val postTitle = binding.newPostTitle.text.toString()
            val postBody = binding.newPostBody.text.toString()
            val userId = 1
            val id = listOfPost.size+1

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
                    val newPost = MvcPostItems(postBody,id,postTitle,userId)
                    listener.mvcSendPost(newPost)
                    dismiss()
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        super.onAttach(context)
        try {
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement UploadDialogListener")
        }

    }

    interface MvcUploadDialogListener{
         fun mvcSendPost(posts: MvcPostItems)
    }

}