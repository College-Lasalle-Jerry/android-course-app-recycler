package com.example.courserecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(
    private val courses: List<Course>,
    private val onItemClickListener: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCourseNo: TextView = itemView.findViewById(R.id.text_course_no)
        val textCourseName: TextView = itemView.findViewById(R.id.text_course_name)
        val textCourseFees: TextView = itemView.findViewById(R.id.text_course_fees)

        fun bind(course: Course) {
            textCourseNo.text = course.courseNo
            textCourseName.text = course.courseName
            textCourseFees.text = "${course.courseFees}$"

            itemView.setOnClickListener {
                onItemClickListener(course)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_list_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

}