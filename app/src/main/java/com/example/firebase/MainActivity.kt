package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database



class MainActivity : AppCompatActivity() {

//    val database = Firebase.database
    val myRef = Firebase.database.getReference("Details")
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id= myRef.push().key


        binding.btnSubmit.setOnClickListener {

            myRef.child(id!!).setValue(model).addOnSuccessListener {
                Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "FAILURE", Toast.LENGTH_SHORT).show()
            }
        }
    }
}