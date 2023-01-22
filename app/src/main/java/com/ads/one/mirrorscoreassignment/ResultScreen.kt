package com.ads.one.mirrorscoreassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ads.one.mirrorscoreassignment.databinding.ActivityResultScreenBinding

class ResultScreen : AppCompatActivity() {
    private lateinit var binding: ActivityResultScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val totalScore = intent.getStringExtra("totalScore")
        val correct = intent.getStringExtra("correct")
        val wrong = intent.getStringExtra("wrong")
        val unattempted = intent.getStringExtra("unattempted")
        Log.d("totalScore", totalScore.toString())
        Log.d("correct", correct.toString())
        Log.d("wrong", wrong.toString())
        Log.d("unattempted", unattempted.toString())
        if (totalScore != null) {
            if(totalScore.toInt() in 8..10){
                binding.remark.text="Good Attempt,Keep it Up!!"
            }else if(totalScore.toInt() in 5..7){
                binding.remark.text="Nice Try,but you can do more better"
            }else{
                binding.remark.text="Please attempt quiz again"
            }
        }
        binding.yourScore.text = totalScore.toString() + "/10"
        binding.correctQues.text = correct.toString() + "/10"
        binding.wrongQues.text = wrong.toString() + "/10"
        binding.unattemptedQues.text = unattempted.toString() + "/10"
        binding.btnGoToHome.setOnClickListener {
            startActivity(Intent(this, QuizSelectionScreen::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, QuizSelectionScreen::class.java))
        finish()
    }
}