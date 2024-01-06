package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.firebase.databinding.ActivityLoginBinding
import com.example.firebase.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    var userName: String ?= null
    var pwd: String ?= null
    lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth= FirebaseAuth.getInstance()
        binding.idTVRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            binding.idPBLoading.visibility= View.VISIBLE
            userName= binding.idEditUserName.text.toString()
            pwd= binding.idEditPwd.text.toString()
            if(userName.isNullOrEmpty()||pwd.isNullOrEmpty()){
                Toast.makeText(this,"Please fill your credentials", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                mAuth.signInWithEmailAndPassword(userName!!, pwd!!).addOnCompleteListener(this) {
                    if(it.isSuccessful){
                        binding.idPBLoading.visibility= View.GONE
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                    else{
                        binding.idPBLoading.visibility= View.GONE
                        Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var user: FirebaseUser? = mAuth.currentUser
        if(user != null){
            var intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}