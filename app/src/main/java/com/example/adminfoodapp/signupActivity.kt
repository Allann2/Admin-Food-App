package com.example.adminfoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import com.example.adminfoodapp.databinding.ActivitySignupBinding
import com.example.adminfoodapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class signupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var restaurantName: String
    private lateinit var database: DatabaseReference


    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialization FireBase
        auth = Firebase.auth
        //initialize Firebase database
        database = Firebase.database.reference


        binding.createButton.setOnClickListener {
            //get text from edittext
            userName = binding.loginName.text.toString().trim()
            restaurantName = binding.loginRestoname.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (userName.isBlank() || restaurantName.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }

        }
        binding.alreadyHaveButton.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
        //location
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, loginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "accountCreation: Failure", task.exception)
            }
        }
    }

    //save data into database
//    private fun saveUserData() {
//        //get text from edittext
//        userName = binding.loginName.text.toString().trim()
//        restaurantName = binding.loginRestoname.text.toString().trim()
//        email = binding.email.text.toString().trim()
//        password = binding.password.text.toString().trim()
//        val admin = UserModel(userName, restaurantName, email, password)
//        val userId = FirebaseAuth.getInstance().currentUser!!.uid
//
//        //save user data into firebase
//        database.child("admin").child(userId).setValue(admin)
//
//    }
    private fun saveUserData() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()
        val userId = auth.currentUser?.uid
        val admin = UserModel(
            loginName = binding.loginName.text.toString().trim(),
            loginRestaurantName = binding.loginRestoname.text.toString().trim(),
            email = email,
            password = password,
            role = "admin" // Set role as "admin"
        )

        userId?.let {
            database.child("admin").child(it).setValue(admin)
                .addOnSuccessListener {
                    Log.d("Database", "Admin data saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.d("Database", "Error saving admin data: ${e.message}")
                }
        }
    }
}