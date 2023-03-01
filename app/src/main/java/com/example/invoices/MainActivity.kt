package com.example.invoices

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.invoices.ui.invoices_list.InvoicesListFragment
import com.example.invoicestesttask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainerView.id, InvoicesListFragment())
                .commit()
        }
    }

}