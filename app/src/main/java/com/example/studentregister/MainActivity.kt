package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDao
import com.example.studentregister.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var nameEText: EditText
    private lateinit var emailEText: EditText
    private lateinit var saveBtn: Button
    private lateinit var clearBtn: Button
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter

    private lateinit var viewModel: StudentViewModel
    private lateinit var selectedStudent: Student
    private var isListItemClicked = false
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
            if (isListItemClicked) {
                updateStudentData()
                clearInput()
            } else {
                saveStudentData()
                clearInput()
            }
        }

        clearBtn.setOnClickListener {
            if (isListItemClicked){
                deleteStudentData()
                clearInput()
            }else{
                clearInput()
            }
        }
        initRecyclerView()

    }

    private fun saveStudentData() {
        val name = nameEText.text.toString()
        val email = emailEText.text.toString()
        val student = Student(0, name, email)
        viewModel.insertStudent(student)
    }

    private fun updateStudentData() {
        viewModel.updateStudent(
            Student(
                selectedStudent.id, nameEText.text.toString(), emailEText.text.toString()
            )
        )
        saveBtn.text = "Save"
        clearBtn.text = "Clear"
        isListItemClicked = false
    }

    private fun deleteStudentData() {
        viewModel.deleteStudent(
            Student(
                selectedStudent.id, nameEText.text.toString(), emailEText.text.toString()
            )
        )
        saveBtn.text = "Save"
        clearBtn.text = "Clear"
        isListItemClicked = false
    }

    private fun clearInput() {
        nameEText.setText("")
        emailEText.setText("")
    }

    private fun initRecyclerView() {
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter { selectedItem: Student ->
            listItemClicked(selectedItem)
        }
        studentRecyclerView.adapter = adapter
        displayStudentList()
    }

    private fun displayStudentList() {
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(student: Student) {
//        Toast.makeText(this, "Student name is: ${student.name}", Toast.LENGTH_LONG).show()
        selectedStudent = student
        saveBtn.text = "Update"
        clearBtn.text = "Delete"
        isListItemClicked = true
        nameEText.setText(selectedStudent.name)
        emailEText.setText(selectedStudent.email)
    }


}