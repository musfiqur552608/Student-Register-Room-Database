package com.example.studentregister.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_data_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    var id:Int,
    @ColumnInfo(name = "student_name")
    var name:String,
    @ColumnInfo(name = "student_email")
    var email: String
)