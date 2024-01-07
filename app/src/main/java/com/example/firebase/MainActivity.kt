package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(), CourseClickInterface{
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var courseRVModalArrayList: ArrayList<CourseRVModal>
    lateinit var courseRVAdapter: CourseRVAdapter
    lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("courses")

        courseRVAdapter= CourseRVAdapter(courseRVModalArrayList,this, this)
        layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.idRVCourses.layoutManager= layoutManager
        binding.idRVCourses.adapter= courseRVAdapter

        binding.idAddFAB.setOnClickListener {
            var intent = Intent(this, AddCourseActivity::class.java)
            startActivity(intent)
        }
        getAllCourses()
    }

    override fun onCourseClick(position: Int) {

    }

    private fun getAllCourses(){
        courseRVModalArrayList.clear()
        databaseReference.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                binding.idPBLoading.visibility= View.GONE
                snapshot.getValue(CourseRVModal::class.java)?.let { courseRVModalArrayList.add(it) }
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                binding.idPBLoading.visibility= View.GONE
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                binding.idPBLoading.visibility= View.GONE
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                binding.idPBLoading.visibility= View.GONE
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}