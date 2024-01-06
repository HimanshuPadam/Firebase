package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityAddCourseBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddCourseActivity : AppCompatActivity() {
    private var courseName: String ?= null
    private var coursePrice: String ?= null
    private var suitedFor: String ?= null
    private var courseImage: String ?= null
    private var courseLink: String ?= null
    private var courseDesc: String ?= null
    private var courseId: String ?= null

    lateinit var courseRVModal: CourseRVModal
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var binding: ActivityAddCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("courses")

        binding.idBtnAddCourse.setOnClickListener {
            courseName=binding.idEditCourseName.text.toString()
            coursePrice= binding.idEditCoursePrice.text.toString()
            suitedFor= binding.idEditCourseSuitedFor.text.toString()
            courseImage= binding.idEditCourseImageLink.text.toString()
            courseLink= binding.idEditCourseLink.text.toString()
            courseDesc= binding.idEditCourseDescription.text.toString()
            courseId= courseName

            courseRVModal= CourseRVModal()
            courseRVModal.getCourseDetails(courseName!!,coursePrice!!,suitedFor!!,courseImage!!,courseLink!!,courseDesc!!,courseId!!)

            databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    databaseReference.child(courseId!!).setValue(courseRVModal)
                    Toast.makeText(this@AddCourseActivity,"Course Added Successfully",Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@AddCourseActivity,AddCourseActivity::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AddCourseActivity,"Error is $error",Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }
}