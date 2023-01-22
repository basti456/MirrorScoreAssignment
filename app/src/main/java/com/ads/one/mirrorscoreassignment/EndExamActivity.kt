package com.ads.one.mirrorscoreassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ads.one.mirrorscoreassignment.databinding.ActivityEndExamBinding

class EndExamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEndExamBinding
    private lateinit var btnAnim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndExamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        binding.viewResult.animation = btnAnim
        binding.viewResult.setOnClickListener {
            val totalScore = intent.getStringExtra("totalScore")
            val correct = intent.getStringExtra("correct")
            val wrong = intent.getStringExtra("wrong")
            val unattempted = intent.getStringExtra("unattempted")
            val intent = Intent(this, ResultScreen::class.java)
            intent.putExtra("totalScore", totalScore.toString())
            intent.putExtra("correct", correct.toString())
            intent.putExtra("wrong", wrong.toString())
            intent.putExtra("unattempted", unattempted.toString())
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}