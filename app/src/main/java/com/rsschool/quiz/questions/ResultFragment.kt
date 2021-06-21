package com.rsschool.quiz.fragmentsQuestions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.InterfaceCommunicator
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.questions.Question1
import kotlin.system.exitProcess

class ResultFragment : Fragment() {
    private var communicator: InterfaceCommunicator? = null
    var bindingClass: FragmentResultBinding? = null
    private val binding get() = bindingClass!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as InterfaceCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.gray, null)

        val answersArray = arguments?.getStringArray(ANSWER_ARRAY_KEY) as Array<String>
        val rightAnswersArray = arrayOf(
            resources.getString(R.string.rightAnswer1),
            resources.getString(R.string.rightAnswer2),
            resources.getString(R.string.rightAnswer3),
            resources.getString(R.string.rightAnswer4),
            resources.getString(R.string.rightAnswer5)
        )
       var result = checkAnswers (answersArray, rightAnswersArray)

        binding.resultTextView.text = "Ваш результат: $result из 5"

        binding.shareImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                generateReportAnswers("Ваш результат: $result из 5", answersArray)
            )
            val chooserTitle = getString(R.string.chooser)
            val chosenIntent = Intent.createChooser(intent, chooserTitle)
            startActivity(chosenIntent)
        }
        binding.backImageView.setOnClickListener {
            communicator?.openFragment(Question1.newInstance(Array<String> (5) { _ -> "" }))

        }
        binding.exitImageView.setOnClickListener {
            exitProcess(0)
        }
    }

    fun checkAnswers(array: Array<String>, rightAnswersArray: Array<String>): Int {
        var result = 0
        for (i in array.indices)
            if (array[i] == rightAnswersArray[i]) result++
        return result
    }

    private fun generateReportAnswers(messageResult: String, answerArray: Array<String>): String {
        var text = "$messageResult\n"
        val questionsArray = arrayOf(
            R.string.question1,
            R.string.question2,
            R.string.question3,
            R.string.question4,
            R.string.question5
        )

        for (i in questionsArray.indices)
            text += "\n${i + 1}) ${resources.getString(questionsArray[i])}" +
                    "\nYour answer: ${answerArray[i]}\n"

        return text
    }



    companion object {
        @JvmStatic
        fun newInstance(answersArray: Array<String>): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putStringArray(ANSWER_ARRAY_KEY, answersArray)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWER_ARRAY_KEY = "RESULT"
    }
}