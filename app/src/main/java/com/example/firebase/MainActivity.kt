package com.example.firebase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.ActivityMainBinding
import com.example.firebase.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), CourseClickInterface{
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var courseRVModalArrayList: ArrayList<CourseRVModal>
    lateinit var courseRVAdapter: CourseRVAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var bottomSheetCL: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheetCL=findViewById(R.id.idCLBSheet)
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

    override fun onCourseClick(position: Int) {
        displayBottomSheet(courseRVModalArrayList[position])
    }

    private fun displayBottomSheet(courseRVModal: CourseRVModal){
        var bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(this)
        var layout: View= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetCL)
        bottomSheetDialog.setContentView(layout)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
        var bindingBS: BottomSheetDialogBinding= BottomSheetDialogBinding.inflate(layoutInflater)
        bindingBS.idTVCourseName.setText(courseRVModal.getCourseName())
        bindingBS.idTVDescription.setText(courseRVModal.getCourseDesc())
        bindingBS.idTVSuitedFor.setText(courseRVModal.getSuitedFor())
        bindingBS.idTVCoursePrice.setText("Rs. "+courseRVModal.getCoursePrice())
        Picasso.get().load(courseRVModal.getCourseImage()).into(bindingBS.idIVCourse)
        bindingBS.idTVCourseName.setText(courseRVModal.getCourseName())

        bindingBS.idBtnEditCourse.setOnClickListener {
            var intent= Intent(this, EditCourseActivity::class.java)
            intent.putExtra("courses",courseRVModal)
            startActivity(intent)
        }

        bindingBS.idBtnViewDetails.setOnClickListener {
            var intent= Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(courseRVModal.getCourseLink()))
            startActivity(intent)
        }
    }
}