package com.example.adminfoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodapp.adapter.DeliveryAdapter
import com.example.adminfoodapp.adapter.PendingOrderAdapter
import com.example.adminfoodapp.databinding.ActivityPendingOrderBinding
import com.example.adminfoodapp.databinding.PendingOrdersItemBinding

class PendingOrderActivity : AppCompatActivity() {
        private lateinit var binding: ActivityPendingOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            finish()
        }

        val orderedCustomerName = arrayListOf(
            "Allan",
            "Keith",
            "Ladera"
        )
        val orderedQuantity = arrayListOf(
            "2",
            "4",
            "6"
        )
        val orderedFoodImage = arrayListOf(
            R.drawable.burbur,
            R.drawable.pankek,
            R.drawable.pankek
        )
        val adapter = PendingOrderAdapter(orderedCustomerName,orderedQuantity, orderedFoodImage, this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this,)
    }
}