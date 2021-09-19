package com.example.wk9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wk9.databinding.ActivityWelcomeBinding
import com.example.wk9.firstImplementation.view.PostActivity
import com.example.wk9.secondImplementation.ui.post.MvcPostActivity

private lateinit var binding: ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="MVVM & MVC Blog"

        binding.btnFirst.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        binding.btnSecond.setOnClickListener {
            val intent = Intent(this, MvcPostActivity::class.java)
            startActivity(intent)
        }
    }
}