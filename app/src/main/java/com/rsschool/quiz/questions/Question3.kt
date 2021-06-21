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
import com.rsschool.quiz.databinding.FragmentQuestion3Binding

class Question3 : Fragment(), IOnBackPressed {
    private var answersArray: Array<String> = emptyArray()
    private var communicator: InterfaceCommunicator? = null
    var bindingClass: FragmentQuestion3Binding? = null
    private val binding get() = bindingClass!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as InterfaceCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentQuestion3Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.cyan_100_dark, null)

        answersArray = arguments?.getStringArray(ANSWER_ARRAY_KEY) as Array<String>

        binding.nextButton.setBackgroundColor(
            binding.previousButton.context.resources.getColor(R.color.gray)
        )

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val id = binding.radioGroup.checkedRadioButtonId
            var chosenRadioButton: RadioButton? = null
            binding.radioGroup.forEach { if (it.id == id) chosenRadioButton = it as RadioButton }
            answersArray[2] = chosenRadioButton?.text.toString()
            binding.nextButton.setBackgroundColor(
                binding.nextButton.context.getColor(R.color.cyan_100))
        }

        binding.toolbar.setOnClickListener {
            communicator?.openFragment(Question2.newInstance(answersArray))
        }
        binding.previousButton.setOnClickListener {
            communicator?.openFragment(Question2.newInstance(answersArray))
        }
        binding.nextButton.setOnClickListener {
            communicator?.openFragment(Question4.newInstance(answersArray))
        }


    }

    override fun onDestroyView() {
        bindingClass = null
        super.onDestroyView()
    }
    override fun onBackPressed(): Boolean {
        communicator?.openFragment(Question2.newInstance(answersArray))
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: Array<String>): Question3 {
            val fragment = Question3()
            val args = Bundle()
            args.putStringArray(ANSWER_ARRAY_KEY, answersArray)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWER_ARRAY_KEY = "RESULT"
    }
}