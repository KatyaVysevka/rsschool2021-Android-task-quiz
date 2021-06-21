package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.questions.Question1
import com.rsschool.quiz.questions.Question2

class MainActivity : AppCompatActivity(), InterfaceCommunicator {
    private val answersArray = Array<String>(5) { _ -> "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(Question1.newInstance(answersArray))
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.container)
        (fragment as? IOnBackPressed)?.onBackPressed()

    }
}