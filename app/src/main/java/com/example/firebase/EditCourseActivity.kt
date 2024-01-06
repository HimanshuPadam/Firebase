package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.firebase.databinding.ActivityAddCourseBinding
import com.example.firebase.databinding.ActivityEditCourseBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Objects

class EditCourseActivity : AppCompatActivity() {
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
    lateinit var binding: ActivityEditCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase= FirebaseDatabase.getInstance()

        courseRVModal = intent.getParcelableExtra("course")!!

        if(courseRVModal!= null){
            binding.idEditCourseName.setText(courseRVModal.getCourseName())
            binding.idEditCoursePrice.setText(courseRVModal.getCoursePrice())
            binding.idEditCourseSuitedFor.setText(courseRVModal.getSuitedFor())
            binding.idEditCourseImageLink.setText(courseRVModal.getCourseImage())
            binding.idEditCourseLink.setText(courseRVModal.getCourseLink())
            binding.idEditCourseDescription.setText(courseRVModal.getCourseDesc())
            courseId= courseRVModal.getCourseId()
        }

        databaseReference=firebaseDatabase.getReference("courses").child(courseId!!)

        binding.idBtnUpdateCourse.setOnClickListener {
            courseName=binding.idEditCourseName.text.toString()
            coursePrice= binding.idEditCoursePrice.text.toString()
            suitedFor= binding.idEditCourseSuitedFor.text.toString()
            courseImage= binding.idEditCourseImageLink.text.toString()
            courseLink= binding.idEditCourseLink.text.toString()
            courseDesc= binding.idEditCourseDescription.text.toString()

            val map = HashMap<String, Any>()
            map["courseName"] = courseName!!
            map["coursePrice"] = coursePrice!!
            map["suitedFor"] = suitedFor!!
            map["courseImage"] = courseImage!!
            map["courseLink"] = courseLink!!
            map["courseDesc"] = courseDesc!!
            map["courseId"] = courseId!!

            databaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    binding.idPBLoading.visibility= View.GONE

                    databaseReference.updateChildren(map)
                    Toast.makeText(this@EditCourseActivity,"Course Updated Successfully", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@EditCourseActivity,MainActivity::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.idPBLoading.visibility= View.VISIBLE

                    Toast.makeText(this@EditCourseActivity,"Failed to Update....Error is $error",Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
        binding.idBtnDeleteCourse.setOnClickListener {
            databaseReference.removeValue()
            Toast.makeText(this@EditCourseActivity,"Course Deleted Successfully", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this@EditCourseActivity,MainActivity::class.java))
        }
    }
}