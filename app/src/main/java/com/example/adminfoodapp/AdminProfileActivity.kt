package com.example.adminfoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfoodapp.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.nameEditText.isEnabled = false
        binding.addressEditText.isEnabled = false
        binding.emailEditText.isEnabled = false
        binding.phoneEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false

        var isEnable = false
        binding.editButton.setOnClickListener {
            isEnable =! isEnable
            binding.nameEditText.isEnabled = isEnable
            binding.addressEditText.isEnabled = isEnable
            binding.emailEditText.isEnabled = isEnable
            binding.phoneEditText.isEnabled = isEnable
            binding.passwordEditText.isEnabled = isEnable
            if (isEnable){
                binding.nameEditText.requestFocus()
            }
        }
    }

}