package com.rsschool.quiz.fragmentsQuestions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.databinding.FragmentMessageBinding


class ReceiveMessageActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MESSAGE: String = "message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FragmentMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        val messageText: String = intent.getStringExtra(EXTRA_MESSAGE).toString()
        binding.gotMessage.text = messageText

    }
}