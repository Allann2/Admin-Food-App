package com.example.adminfoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminfoodapp.databinding.ActivityLoginBinding
import com.example.adminfoodapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var restaurantName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.loginButton.setOnClickListener {
            email = binding.loginEmail.text.toString().trim()
            password = binding.loginPass.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all Details", Toast.LENGTH_SHORT).show()
            } else {
                signInOrCreateAccount(email, password)
            }
        }

        binding.donthaveaccountButton.setOnClickListener {
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInOrCreateAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val admin = auth.currentUser
                updateUi(admin)
            } else {
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val admin = auth.currentUser
                saveUserData()
                updateUi(admin)
            } else {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createUserAccount: Authentication Failed", task.exception)
            }
        }
    }

    private fun saveUserData() {
        email = binding.loginEmail.text.toString().trim()
        password = binding.loginPass.text.toString().trim()

        val admin = UserModel(email = email, password = password)
        val userId = auth.currentUser?.uid

        userId?.let {
            database.child("admin").child(it).setValue(admin)
                .addOnSuccessListener {
                    Log.d("Database", "User data saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.d("Database", "Error saving user data: ${e.message}")
                }
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}