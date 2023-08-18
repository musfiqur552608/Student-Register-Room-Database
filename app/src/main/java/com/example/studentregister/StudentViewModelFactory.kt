package com.example.studentregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentregister.db.StudentDao
import java.lang.IllegalArgumentException

class StudentViewModelFactory(private val dao: StudentDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)){
            return StudentViewModel(dao) as T
        }
        throw  IllegalArgumentException("Unknown View Model Class")
    }
}