package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDao
import com.example.studentregister.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var nameEText : EditText
    private lateinit var emailEText : EditText
    private lateinit var saveBtn : Button
    private lateinit var clearBtn : Button
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter

    private lateinit var viewModel:StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEText = findViewById(R.id.nameEtx)
        emailEText = findViewById(R.id.emailEtx)
        saveBtn = findViewById(R.id.saveBtn)
        clearBtn = findViewById(R.id.clearBtn)
        studentRecyclerView = findViewById(R.id.recyclerView)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        saveBtn.setOnClickListener {
            saveStudentData()
            clearInput()
        }

        clearBtn.setOnClickListener {
            clearInput()
        }
        initRecyclerView()

    }

    private fun saveStudentData(){
        val name = nameEText.text.toString()
        val email = emailEText.text.toString()
        val student = Student(0, name, email)
        viewModel.insertStudent(student)
    }

    private fun clearInput(){
        nameEText.setText("")
        emailEText.setText("")
    }

    private fun initRecyclerView(){
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter()
        studentRecyclerView.adapter = adapter
        displayStudentList()
    }

    private fun displayStudentList(){
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }
}