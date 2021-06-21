package com.rsschool.quiz.questions

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import com.rsschool.quiz.IOnBackPressed
import com.rsschool.quiz.InterfaceCommunicator
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestion5Binding
import com.rsschool.quiz.fragmentsQuestions.ResultFragment

class Question5 : Fragment(), IOnBackPressed {
    private var answersArray: Array<String> = emptyArray()
    private var communicator: InterfaceCommunicator? = null
    var bindingClass: FragmentQuestion5Binding? = null
    private val binding get() = bindingClass!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as InterfaceCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentQuestion5Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.light_green_100_dark, null)

        answersArray = arguments?.getStringArray(ANSWER_ARRAY_KEY) as Array<String>

        binding.nextButton.isEnabled = false
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.nextButton.isEnabled = true
            val id = binding.radioGroup.checkedRadioButtonId
            var chosenRadioButton: RadioButton? = null
            binding.radioGroup.forEach { if (it.id == id) chosenRadioButton = it as RadioButton }
            answersArray[4] = chosenRadioButton?.text.toString()
            binding.nextButton.setBackgroundColor(
                binding.nextButton.context.getColor(R.color.light_green_100)
            )
        }

        binding.toolbar.setOnClickListener {
            communicator?.openFragment(Question4.newInstance(answersArray))
        }
        binding.previousButton.setOnClickListener {
            communicator?.openFragment(Question4.newInstance(answersArray))
        }
        binding.nextButton.setOnClickListener {
            communicator?.openFragment(ResultFragment.newInstance(answersArray))
        }


    }

    override fun onBackPressed(): Boolean {
        communicator?.openFragment(Question4.newInstance(answersArray))
        return false
    }

    override fun onDestroyView() {
        bindingClass = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: Array<String>): Question5 {
            val fragment = Question5()
            val args = Bundle()
            args.putStringArray(ANSWER_ARRAY_KEY, answersArray)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWER_ARRAY_KEY = "RESULT"
    }
}