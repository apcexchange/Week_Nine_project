package com.example.wk9.firstImplementation.model.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wk9.databinding.FragmentAddCommentDialogBinding
import com.example.wk9.firstImplementation.model.Comment
import java.lang.ClassCastException

class AddCommentDialog(private val arrayListOfComment: ArrayList<Comment>, private val listener: UploadCommentInterface) : DialogFragment() {

private var _binding: FragmentAddCommentDialogBinding? = null
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
        _binding = FragmentAddCommentDialogBinding.inflate(inflater,container,false)
        return binding.root
//        inflater.inflate(R.layout.fragment_add_comment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btPostComment.setOnClickListener {
            val commentName = binding.newCommentName.text.toString()
            val commentEmail = binding.newCommentEmail.text.toString()
            val commentBody = binding.newCommentBody.text.toString()
            val userId = 1
            val id = arrayListOfComment.size+1

            when {
                commentName.isEmpty() -> {
                    binding.newCommentName.error = " Name cant be empty"
                    return@setOnClickListener
                }

                commentEmail.isEmpty() -> {
                    binding.newCommentEmail.error = " email cant be empty"
                    return@setOnClickListener
                }
                commentBody.isEmpty() -> {
                    binding.newCommentBody.error = " body can't b empty"
                }
                else -> {
                    val newComment = Comment(id,userId,commentName,commentEmail,commentBody)
                    listener.sendComment(newComment)
                    dismiss()

                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {

        }catch (e:ClassCastException){
            throw ClassCastException(context.toString() + "must implement UploadDialogListener")
        }
    }


    interface UploadCommentInterface{
        fun sendComment(comment: Comment)
    }

}