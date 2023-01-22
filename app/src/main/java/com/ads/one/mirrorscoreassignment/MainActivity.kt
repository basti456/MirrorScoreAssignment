package com.ads.one.mirrorscoreassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.ads.one.mirrorscoreassignment.adapters.QuestionGridAdapter
import com.ads.one.mirrorscoreassignment.adapters.QuizQuestionAdapter
import com.ads.one.mirrorscoreassignment.api.QuizService
import com.ads.one.mirrorscoreassignment.api.RetrofitHelper
import com.ads.one.mirrorscoreassignment.models.Result
import com.ads.one.mirrorscoreassignment.repository.QuizRepository
import com.ads.one.mirrorscoreassignment.viewModels.MainViewModel
import com.ads.one.mirrorscoreassignment.viewModels.MainViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var examRecyclerView: RecyclerView
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var candidateImage: ImageView
    private var quesPos: Int = 0
    private lateinit var currentQues: TextView
    private lateinit var candidateName: TextView
    private lateinit var examName: TextView
    private lateinit var subjectName: TextView
    private lateinit var questionGrid: ImageView
    private lateinit var saveAndNextButton: Button
    private lateinit var mrk_for_review_a_next: Button
    private lateinit var submitButton: Button
    private lateinit var examTime: TextView
    private lateinit var examTimer: TextView
    private lateinit var questionGridAdapter: QuestionGridAdapter
    private lateinit var dialog: BottomSheetDialog
    private lateinit var questionListGrid: GridView
    private lateinit var buttonAnswered: TextView
    private lateinit var buttonNotAnswered: TextView
    private lateinit var buttonNotVisited: TextView
    private lateinit var buttonMarkForReview: TextView
    private lateinit var visitedMap: HashMap<Int, Int>
    private lateinit var answeredMap: HashMap<Int, Int>
    private lateinit var buttonAnsweredAndMarkForReview: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        visitedMap = HashMap()
        answeredMap = HashMap()
        examRecyclerView = findViewById(R.id.exam_recycler_view)
        backButton = findViewById(R.id.back_btn)
        nextButton = findViewById(R.id.next_btn)
        examTime = findViewById(R.id.remaining_time)
        examName = findViewById(R.id.exam_name)
        examTimer = findViewById(R.id.remaining_timer)
        subjectName = findViewById(R.id.subject_name)
        currentQues = findViewById(R.id.current_ques_no)
        questionGrid = findViewById(R.id.questions_grid)
        candidateImage = findViewById(R.id.candidate_image)
        mrk_for_review_a_next = findViewById(R.id.mark_review_next_btn)
        saveAndNextButton = findViewById(R.id.Save_next_btn)
        candidateName = findViewById(R.id.candidate_name)
        submitButton = findViewById(R.id.submit_btn)
        val quizService = RetrofitHelper.getInstance().create(QuizService::class.java)
        val repository = QuizRepository(quizService)
        val category = intent.getStringExtra("category").toString()
        val difficulty = intent.getStringExtra("difficulty").toString()
        val categoryName = intent.getStringExtra("categoryName").toString()
        subjectName.text = categoryName
        mainViewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(repository, 10, category.toInt(), difficulty)
            )[MainViewModel::class.java]
        mainViewModel.quizzes.observe(this, Observer {
            val sz = it.results.size
            //Log.d("output", it.results.toString())
            for (i in 1..sz) {
                visitedMap[i] = 0
                answeredMap[i] = 0
            }
            questionGridAdapter = QuestionGridAdapter(sz, visitedMap)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            examRecyclerView.layoutManager = layoutManager
            examRecyclerView.setItemViewCacheSize(sz)
            val examQuestionAdapter = QuizQuestionAdapter(
                this,
                it.results as ArrayList<Result>,
                visitedMap,
                answeredMap
            )
            examRecyclerView.adapter = examQuestionAdapter
            currentQues.text = (quesPos + 1).toString() + "/" + sz.toString()
        })
        setSnapHelper()
        setClickListeners()
        startTimer()
    }

    private fun setSnapHelper() {
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(examRecyclerView)
        examRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val view = snapHelper.findSnapView(examRecyclerView.layoutManager)
                quesPos = examRecyclerView.layoutManager!!.getPosition(view!!)
                currentQues.text = (quesPos + 1).toString() + "/" + 10.toString()
                if (visitedMap[quesPos + 1] == 0) {
                    visitedMap[quesPos + 1] = 1
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setClickListeners() {
        nextButton.setOnClickListener {
            if (quesPos < 9) {
                examRecyclerView.smoothScrollToPosition(quesPos + 1)
            }

        }
        backButton.setOnClickListener {
            if (quesPos > 0) {
                examRecyclerView.smoothScrollToPosition(quesPos - 1)
            }
        }
        saveAndNextButton.setOnClickListener {
            if (quesPos < 9) {
                examRecyclerView.smoothScrollToPosition(quesPos + 1)
            }
        }
        candidateName.setOnClickListener {

        }
        questionGrid.setOnClickListener {
            questionGridAdapter.notifyDataSetChanged()
            showBottomSheetDialog()
        }
        mrk_for_review_a_next.setOnClickListener {
            if (visitedMap[quesPos + 1] == 2) {
                visitedMap[quesPos + 1] = 4
            } else {
                visitedMap[quesPos + 1] = 3
            }
            if (quesPos < 9) {
                examRecyclerView.smoothScrollToPosition(quesPos + 1)
            }
        }
        submitButton.setOnClickListener {
            var totalScore = 0
            var correct = 0
            var wrong = 0
            var unattempted = 0
            for (i in 1..10) {
                if (answeredMap[i] == 1) {
                    correct++
                    totalScore++
                } else {
                    wrong++
                }
                if (visitedMap[i] == 1) {
                    unattempted++
                }
            }

            val intent = Intent(this, EndExamActivity::class.java)
            intent.putExtra("totalScore", totalScore.toString())
            intent.putExtra("correct", correct.toString())
            intent.putExtra("wrong", wrong.toString())
            intent.putExtra("unattempted", unattempted.toString())
            startActivity(intent)
            finish()
        }
    }

    private fun startTimer() {
        val examinationTiming = (10 * 60 * 1000).toLong()
        val timer: CountDownTimer = object : CountDownTimer(examinationTiming + 1000, 1000) {
            override fun onTick(timeRemaining: Long) {
                val time = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(timeRemaining),
                    TimeUnit.MILLISECONDS.toMinutes(timeRemaining) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(timeRemaining)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(timeRemaining) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(timeRemaining)
                    )
                )
                examTimer.text = time
            }

            override fun onFinish() {
                //show progress bar for finishing the test and submitting the answers to database..
                startActivity(Intent(this@MainActivity, EndExamActivity::class.java))
                finish()
            }
        }
        timer.start()
    }

    private fun showBottomSheetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.question_list_layout, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        buttonAnswered = dialogView.findViewById(R.id.answered_button)
        buttonNotAnswered = dialogView.findViewById(R.id.notAnsweredButton)
        buttonNotVisited = dialogView.findViewById(R.id.notVisited)
        buttonMarkForReview = dialogView.findViewById(R.id.mark_for_review_button)
        buttonAnsweredAndMarkForReview = dialogView.findViewById(R.id.answer_and_mark_button)
        questionListGrid = dialogView.findViewById(R.id.questionGridView)
        buttonAnswered.setBackgroundResource(R.drawable.button_answered)
        buttonNotAnswered.setBackgroundResource(R.drawable.button_not_answered)
        buttonNotVisited.setBackgroundResource(R.drawable.button_not_visited)
        buttonAnsweredAndMarkForReview.setBackgroundResource(R.drawable.button_answered_and_mark_review)
        buttonMarkForReview.setBackgroundResource(R.drawable.button_marked_for_review)
        questionGridAdapter = QuestionGridAdapter(10, visitedMap)
        questionListGrid.adapter = questionGridAdapter
        questionGridAdapter.notifyDataSetChanged()
        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}