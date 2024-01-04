package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.firebase.databinding.ActivityRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class RegistrationActivity : AppCompatActivity() {
    var userName: String ?= null
    var pwd: String ?= null
    var cnfPwd: String ?= null
    lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth= FirebaseAuth.getInstance()
        binding.idTVLogin.setOnClickListener {
            val intent =Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            binding.idPBLoading.visibility=View.VISIBLE
            userName= binding.idEditUserName.text.toString()
            pwd= binding.idEditPwd.text.toString()
            cnfPwd= binding.idEditCnfPwd.text.toString()
            if(!pwd.equals(cnfPwd)){
                Toast.makeText(this,"Password doesn't match,Please re-enter your password",Toast.LENGTH_SHORT)
                    .show()
            }
            else if(userName.isNullOrEmpty()||pwd.isNullOrEmpty()||cnfPwd.isNullOrEmpty()){
                Toast.makeText(this,"Please fill your credentials",Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                mAuth.createUserWithEmailAndPassword(userName!!, pwd!!).addOnCompleteListener(this) {
                    if(it.isSuccessful){
                        binding.idPBLoading.visibility= View.GONE
                        Toast.makeText(this, "User registered successfully",Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                    else{
                        binding.idPBLoading.visibility= View.GONE
                        Toast.makeText(this, "Failed to register user",Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}