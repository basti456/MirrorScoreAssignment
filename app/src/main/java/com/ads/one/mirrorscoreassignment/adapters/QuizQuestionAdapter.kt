package com.ads.one.mirrorscoreassignment.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.ads.one.mirrorscoreassignment.R
import com.ads.one.mirrorscoreassignment.models.Result

class QuizQuestionAdapter(
    private val context: Context,
    private var quizList: ArrayList<Result>,
    private var visitedMap: HashMap<Int, Int>,
    private var answeredMap: HashMap<Int, Int>
) : RecyclerView.Adapter<QuizQuestionAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quesNo: TextView = itemView.findViewById(R.id.exam_question_number)
        val marksDistribution: TextView = itemView.findViewById(R.id.marks_distribution)
        val questionDetails: TextView = itemView.findViewById(R.id.exam_questionDescription)
        val option1: RadioButton = itemView.findViewById(R.id.option_1)
        val option2: RadioButton = itemView.findViewById(R.id.option_2)
        val option3: RadioButton = itemView.findViewById(R.id.option_3)
        val option4: RadioButton = itemView.findViewById(R.id.option_4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_quiz_layout, parent, false);
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.quesNo.text = "Ques No. " + (position + 1).toString()
        holder.marksDistribution.text = "Max. Score:1|Min. Score:0"
        holder.questionDetails.text = HtmlCompat.fromHtml(
            quizList[position].question,
            0
        )
        holder.option1.text = HtmlCompat.fromHtml(
            quizList[position].correct_answer,
            0
        )
        holder.option2.text = HtmlCompat.fromHtml(
            quizList[position].incorrect_answers[0],
            0
        )
        holder.option3.text = HtmlCompat.fromHtml(
            quizList[position].incorrect_answers[1],
            0
        )
        holder.option4.text = HtmlCompat.fromHtml(
            quizList[position].incorrect_answers[2],
            0
        )
        var is1 = false
        var is2 = false
        var is3 = false
        var is4 = false
        holder.option1.setOnCheckedChangeListener { _, b ->
            is1 = b
            is2 = false
            is3 = false
            is4 = false
            visitedMap[position + 1] = 2
            answeredMap[position + 1] = 1
        }
        holder.option2.setOnCheckedChangeListener { _, b ->
            is2 = b
            is1 = false
            is3 = false
            is4 = false
            visitedMap[position + 1] = 2
            answeredMap[position + 1] = 0
        }
        holder.option3.setOnCheckedChangeListener { _, b ->
            is3 = b
            is2 = false
            is1 = false
            is4 = false
            visitedMap[position + 1] = 2
            answeredMap[position + 1] = 0
        }
        holder.option4.setOnCheckedChangeListener { _, b ->
            is4 = b
            is2 = false
            is3 = false
            is1 = false
            visitedMap[position + 1] = 2
            answeredMap[position + 1] = 0
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}