package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    companion object{
        private const val START_TIME = "startTimePicker"
        private const val END_TIME ="endTime"

    }

    private lateinit var viewModelCourse: AddCourseViewModel
    private lateinit var viewCourse: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddViewModelFactory.create(this)
        viewModelCourse = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
        viewModelCourse.saved.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                onBackPressed()
            } else {
                Toast.makeText(this, getString(R.string.input_empty_message), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun addCourse() {
        val courseLayout = findViewById<TextInputLayout>(R.id.course)
        val course = findViewById<TextInputEditText>(R.id.course_name).text.toString().trim()
        val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString().trim()
        val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString().trim()

        if (course.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()) {
            val day = findViewById<Spinner>(R.id.spinner).selectedItemPosition
            val lecture = findViewById<TextInputEditText>(R.id.ed_lect).text.toString().trim()
            val note = findViewById<TextInputEditText>(R.id.note).text.toString().trim()

            viewModelCourse.insertCourse(course, day, startTime, endTime, lecture, note)
            finish()
        } else {
            courseLayout.error = getString(R.string.input_empty_message)
        }
    }

    fun startTimePicker(view: View) {
        TimePickerFragment().show(supportFragmentManager, START_TIME)
        viewCourse = view
    }

    fun endTimePicker(view: View) {
        TimePickerFragment().show(supportFragmentManager, END_TIME)
        viewCourse = view
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                addCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            START_TIME -> findViewById<TextView>(R.id.tv_start_time).text =
                timeFormat.format(calendar.time)

            END_TIME -> findViewById<TextView>(R.id.tv_end_time).text =
                timeFormat.format(calendar.time)
        }
    }
}