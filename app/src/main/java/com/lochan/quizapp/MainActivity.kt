package com.lochan.quizapp

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val questions = listOf(
                Question("What does CPU stand for?",listOf("Central Processing Unit","Computer Personal Unit","Central Personal Unit","Central Processor Unit"),0),
                Question("What is the Basic Unit of Digital Data?",listOf("Byte","Bit","Nibble","MegaByte"),1),
                Question("Which device is used to input text into a computer?",listOf("Monitor","Keyboard","Printer","Speaker"),1),
                Question("Which part of the computer is known as the brain of the computer?",listOf("RAM","Hard Disk","CPU","Monitor"),2),
                Question("What does RAM stand for?",listOf("Random Access Memory","Read Access Memory","Rapid Access Memory","Run Access Memory"),0),
                Question("Which of the following is an output device?",listOf("Mouse","Keyboard","Monitor","Scanner"),2),
                Question("What does WWW stand for?",listOf("World Wide Web","World Web Wide","Wide World Web","Web World Wide"),0),
                Question("Which company developed the Windows operating system?",listOf("Apple","Google","Microsoft","IBM"),2),
                Question("Which key is used to delete text to the left of the cursor?",listOf("Delete","Backspace","Shift","Ctrl"),1),
                Question("What is the full form of USB?",listOf("Universal Serial Bus","Universal System Bus","Unified Serial Bus","United System Bus"),0),
                Question("Which of the following is a web browser?",listOf("Windows","Chrome","Linux","Android"),1),
                Question("What is the default extension of a Kotlin file?",listOf(".java",".kt",".kotlin",".kod"),1),
                Question("Which language is primarily used for Android development?",listOf("Python","Kotlin","C#","PHP"),1),
                Question("What does HTML stand for?",listOf("Hyper Text Markup Language","High Text Markup Language","Hyper Tool Markup Language","Home Text Markup Language"),0),
                Question("Which symbol is used to end a statement in Java?",listOf(".",":",";","?"),2),
                Question("What does API stand for?",listOf("Application Programming Interface","Application Process Interface","Applied Programming Interface","Application Program Integration"),0),
                Question("Which data structure follows the FIFO principle?",listOf("Stack","Queue","Tree","Graph"),1),
                Question("Which keyword is used to create a class in Kotlin?",listOf("class","Class","new","object"),0),
                Question("Which company developed Kotlin?",listOf("Google","Microsoft","JetBrains","Oracle"),2),
                Question("What is the value of 2 + 2 * 3?",listOf("12","8","6","10"),1)

    )

    private var currentQuestionIndex = 0
    private var totalQuestionsAsked = 0
    private var score = 0
    private lateinit var questionTextView: TextView
    private lateinit var optionRadioGroup: RadioGroup
    private lateinit var nextButton: Button
    private lateinit var background: RelativeLayout
    private lateinit var scoreTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    questionTextView = findViewById(R.id.questionTextView)
        optionRadioGroup = findViewById(R.id.RadioGroup)
        nextButton = findViewById(R.id.nextButton)
        background = findViewById(R.id.main_background)
        scoreTextView = findViewById(R.id.scoreTextView)

        displayQuestions()

        nextButton.setOnClickListener(View.OnClickListener{
            val selectedOptionID = optionRadioGroup.checkedRadioButtonId

            if(selectedOptionID != -1){
                val selectedOptionIndex = optionRadioGroup.indexOfChild(findViewById(selectedOptionID))

                if(selectedOptionIndex == questions[currentQuestionIndex].correctOptionIndex){
                    score++
                    background.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                }
                else{
                    background.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
                }
                totalQuestionsAsked++
                updateScore()
                Handler().postDelayed({
                    background.setBackgroundColor((Color.parseColor("#252525")))
                    currentQuestionIndex++

                    if(currentQuestionIndex < questions.size){
                        displayQuestions()
                    }
                    else{
                        showQuizResultDialog()
                    }
                },1000)
            }else {

            }
        })
    }

    private fun displayQuestions(){
        val question = questions[currentQuestionIndex]
        questionTextView.text = question.text
        for (i in 0 until optionRadioGroup.childCount){
            val radioButton = optionRadioGroup.getChildAt(i) as RadioButton
            radioButton.text = question.options[i]
            radioButton.isChecked = false
        }
    }

    private fun updateScore() {
        val scoreText = "Score: $score out of $totalQuestionsAsked"
        scoreTextView.text = scoreText

    }

    private fun showQuizResultDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Quiz completed! Your score is $score out of $totalQuestionsAsked.")
        .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener{dialog,id ->
                currentQuestionIndex = 0
                totalQuestionsAsked = 0
                score = 0
                updateScore()
                displayQuestions()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Quiz Results")
        alert.show()

    }
}
data class Question(val text: String, val options: List<String>, val correctOptionIndex: Int)
