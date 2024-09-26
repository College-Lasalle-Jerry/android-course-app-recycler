package com.example.courserecycler

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var fabAddCourse: FloatingActionButton
    private var courses = mutableListOf<Course>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        courses = mutableListOf(
            Course("MIS 101", "Intro. to Info. System", 140.0),
            Course("MIS 301", "System Analysis", 35.0),
            Course("MIS 441", "Database Management", 12.0),
            Course("CS 155", "Programming in C++", 90.0),
            Course("MIS 451", "Web-Based Systems", 30.0),
            Course("MIS 551", "Advanced Web", 30.0),
            Course("MIS 651", "Advanced Java", 30.0)
        )
        recyclerView = findViewById(R.id.recycler_view_courses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        courseAdapter = CourseAdapter(courses) { course ->
            // Handle the click event for each item
            val intent = CourseUpdateActivity.newIntent(this, course)
            index = courses.indexOf(course)
            startActivityIntent.launch(intent)
            Toast.makeText(this, "Clicked item ${course.courseName}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = courseAdapter

        fabAddCourse = findViewById(R.id.fab_add_course)
        fabAddCourse.setOnClickListener {
            // Start CourseUpdateActivity to add a new course
            val intent = CourseUpdateActivity.newIntent(this, Course("", "", 0.0))
            startActivityIntent.launch(intent)
        }
    }
    // Activity result launcher for course update
//    private val startActivityIntent = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        if (result.resultCode != Activity.RESULT_OK) {
//            return@registerForActivityResult
//        }
//
//        val courseUpdatedInfo = result.data?.let {
//            CourseUpdateActivity.sentMessageCourseUpdateDetails(it)
//        }
//        courseUpdatedInfo?.let {
//            // Update the course in the list and refresh the RecyclerView
//            courses[index] = it
//            courseAdapter.notifyDataSetChanged()
//        }
//    }

    // Activity result launcher to handle the result from CourseUpdateActivity
    private val startActivityIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val courseUpdatedInfo = result.data?.let {
                CourseUpdateActivity.sentMessageCourseUpdateDetails(it)
            }
            courseUpdatedInfo?.let {
                if (index >= 0 && index < courses.size) {
                    // Update an existing course
                    courses[index] = it
                } else {
                    // Add a new course
                    courses.add(it)
                }
                // Refresh the RecyclerView
                courseAdapter.notifyDataSetChanged()
            }
        }
    }
}