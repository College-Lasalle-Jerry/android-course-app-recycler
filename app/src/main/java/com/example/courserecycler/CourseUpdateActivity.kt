package com.example.courserecycler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class CourseUpdateActivity : AppCompatActivity() {
    private lateinit var editTextCourseNo: TextInputEditText
    private lateinit var editTextCourseName: TextInputEditText
    private lateinit var editTextCourseFees: TextInputEditText

    private lateinit var buttonUpdateCourseDetails: Button

    private lateinit var course: Course

    companion object {
        const val EXTRA_COURSE = "course"

        // Factory method for creating an intent to launch this activity
        fun newIntent(context: Context, course: Course): Intent {
            val intent = Intent(context, CourseUpdateActivity::class.java)
            intent.putExtra(EXTRA_COURSE, course)  // Pass the course as an extra
            return intent
        }

        // Helper function to extract the updated course details from the result intent
        fun sentMessageCourseUpdateDetails(resultIntent: Intent): Course {
            return resultIntent.getParcelableExtra(EXTRA_COURSE)!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        course = intent.getParcelableExtra("course", Course::class.java) as Course

        // link the files

        editTextCourseNo = findViewById(R.id.edit_text_course_no)
        editTextCourseName = findViewById(R.id.edit_text_course_name)
        editTextCourseFees = findViewById(R.id.edit_text_course_fees)

        buttonUpdateCourseDetails = findViewById(R.id.button_update_course_details)

        editTextCourseNo.setText(course.courseNo)
        editTextCourseName.setText(course.courseName)
        editTextCourseFees.setText(course.courseFees.toString())

        buttonUpdateCourseDetails.setOnClickListener {
            setCourseUpdateDetails(course)
        }
    }

    private fun setCourseUpdateDetails(course: Course) {
        course.courseNo = editTextCourseNo.text.toString()
        course.courseName = editTextCourseName.text.toString()
        course.courseFees = editTextCourseFees.text.toString().toDouble()

        val intent = Intent()
        intent.putExtra("course", course)
        setResult(RESULT_OK, intent)
        finish() // destorys the activity.
    }
}