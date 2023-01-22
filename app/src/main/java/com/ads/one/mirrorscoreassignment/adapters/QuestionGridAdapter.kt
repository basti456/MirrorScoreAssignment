package com.ads.one.mirrorscoreassignment.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ads.one.mirrorscoreassignment.R

class QuestionGridAdapter(
    private val noOfQuestions: Int,
    private val visitedMap: HashMap<Int, Int>
) : BaseAdapter() {
    override fun getCount(): Int {
        return noOfQuestions
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        var myView = view
        if (myView == null) {
            myView = LayoutInflater.from(viewGroup!!.context)
                .inflate(R.layout.question_grid_item, viewGroup, false)
        }
        val quesNumber: TextView = myView!!.findViewById(R.id.ques_number)
        quesNumber.text = (i + 1).toString()
        when (visitedMap[i + 1]) {
            2 -> {
                quesNumber.setBackgroundResource(R.drawable.button_answered)
            }
            1 -> {
                quesNumber.setBackgroundResource(R.drawable.button_not_answered)
            }
            0 -> {
                quesNumber.setBackgroundResource(R.drawable.button_not_visited)
            }
            3 -> {
                quesNumber.setBackgroundResource(R.drawable.button_marked_for_review)
            }
            4 -> {
                quesNumber.setBackgroundResource(R.drawable.button_answered_and_mark_review)
            }
        }
        return myView
    }
}