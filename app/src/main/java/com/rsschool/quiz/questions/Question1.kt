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
import com.rsschool.quiz.InterfaceCommunicator
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestion1Binding

class Question1 : Fragment() {
    private var answersArray: Array<String> = emptyArray()
    private var communicator: InterfaceCommunicator? = null
    var bindingClass: FragmentQuestion1Binding? = null
    private val binding get() = bindingClass!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as InterfaceCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentQuestion1Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.deep_orange_100_dark, null)

        answersArray = arguments?.getStringArray(ANSWER_ARRAY_KEY) as Array<String>
        binding.previousButton.setBackgroundColor(
            binding.previousButton.context.resources.getColor(R.color.gray)
        )
        binding.nextButton.setBackgroundColor(
            binding.previousButton.context.resources.getColor(R.color.gray)
        )

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            val id = binding.radioGroup.checkedRadioButtonId
            var chosenRadioButton: RadioButton? = null
            binding.radioGroup.forEach { if (it.id == id) chosenRadioButton = it as RadioButton }
            answersArray[0] = chosenRadioButton?.text.toString()
            binding.nextButton.setBackgroundColor(
                binding.nextButton.context.getColor(R.color.deep_orange_100)
            )
        }

        binding.nextButton.setOnClickListener {
            communicator?.openFragment(Question2.newInstance(answersArray))
        }


    }

    override fun onDestroyView() {
        bindingClass = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: Array<String>): Question1 {
            val fragment = Question1()
            val args = Bundle()
            args.putStringArray(ANSWER_ARRAY_KEY, answersArray)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWER_ARRAY_KEY = "RESULT"
    }
}