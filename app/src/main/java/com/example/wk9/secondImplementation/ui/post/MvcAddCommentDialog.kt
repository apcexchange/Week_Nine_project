package com.example.wk9.secondImplementation.ui.post

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wk9.R
import com.example.wk9.databinding.FragmentMvcAddCommentDialogBinding
import com.example.wk9.firstImplementation.model.Comment
import com.example.wk9.firstImplementation.view.AddCommentDialog
import com.example.wk9.secondImplementation.model.data.mvcComment.MvcCommentItems
import java.lang.ClassCastException

class MvcAddCommentDialog(private val listOfComment: ArrayList<MvcCommentItems>,private val listener: MvcUploadCommentInterface) : DialogFragment() {
   private var _binding: FragmentMvcAddCommentDialogBinding? = null
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
        _binding = FragmentMvcAddCommentDialogBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btPostComment.setOnClickListener {
            val commentName = binding.newCommentName.text.toString()
            val commentEmail = binding.newCommentEmail.text.toString()
            val commentBody = binding.newCommentBody.text.toString()
            val userId = 1
            val id = listOfComment.size+1

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
                    val newComment = MvcCommentItems(commentBody,commentEmail,id,commentName,userId)
                    listener.sendComment(newComment)
                    dismiss()

                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {

        }catch (e: ClassCastException){
            throw ClassCastException(context.toString() + "must implement UploadDialogListener")
        }
    }

    interface MvcUploadCommentInterface{
        fun sendComment(comment: MvcCommentItems)
    }

}