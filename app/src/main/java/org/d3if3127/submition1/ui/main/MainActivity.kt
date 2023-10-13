package org.d3if3127.submition1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.ui.ViewModelFactory
import org.d3if3127.submition1.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionSet()
    }
    private fun actionSet(){
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}