package com.ads.one.mirrorscoreassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import com.ads.one.mirrorscoreassignment.databinding.ActivityQuizSelectionScreenBinding

class QuizSelectionScreen : AppCompatActivity() {
    private var selectedCategory = 0
    private var selectedDifficulty = ""
    private var categoryName = ""
    private var doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityQuizSelectionScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizSelectionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryList = listOf(
            "General Knowledge", "Entertainment: Books", "Entertainment: Film",
            "Entertainment: Music",
            "Entertainment: Musicals & Theatres",
            "Entertainment: Television",
            "Entertainment: Video Games",
            "Entertainment: Board Games",
            "Science & Nature",
            "Science: Computers",
            "Science: Mathematics",
            "Mythology",
            "Sports",
            "Geography",
            "History",
            "Politics",
            "Art",
            "Celebrities",
            "Animals",
            "Vehicles",
            "Entertainment: Comics",
            "Science: Gadgets",
            "Entertainment: Japanese Anime & Manga",
            "Entertainment: Cartoon & Animations"
        )
        val difficultyList = listOf("Easy", "Medium", "Hard")
        val adapterCategory = ArrayAdapter(this, R.layout.dropdown_item, categoryList)
        val adapterDifficulty = ArrayAdapter(this, R.layout.dropdown_item, difficultyList)
        binding.autoCompleteTVCategory.setAdapter(adapterCategory)
        binding.autoCompleteTVDifficulty.setAdapter(adapterDifficulty)
        binding.autoCompleteTVCategory.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Log.d("position", position.toString())
                categoryName = categoryList[position]
                selectedCategory = position + 10
            }
        binding.autoCompleteTVDifficulty.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedDifficulty = when (position) {
                    0 -> {
                        "easy"
                    }
                    1 -> {
                        "medium"
                    }
                    else -> {
                        "hard"
                    }
                }
            }
        binding.btnStartQuiz.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if (selectedDifficulty == "" || selectedCategory == 0) {
                Toast.makeText(this, "Please select difficulty and category", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            intent.putExtra("difficulty", selectedDifficulty)
            intent.putExtra("category", selectedCategory.toString())
            intent.putExtra("categoryName", categoryName)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}