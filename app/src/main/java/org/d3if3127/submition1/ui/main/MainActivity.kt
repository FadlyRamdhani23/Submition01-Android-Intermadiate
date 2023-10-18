package org.d3if3127.submition1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if3127.submition1.R
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.ui.StoryAdapter
import org.d3if3127.submition1.ui.ViewModelFactory
import org.d3if3127.submition1.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var storiesAdapter: StoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        showLoading(true)
        actionSet()
    }
    private fun actionSet(){
        viewModel.stories.observe(this) { storyResponse ->
            val stories = storyResponse?.listStory ?: emptyList()
            storiesAdapter.submitList(stories)
            showLoading(false)
        }
        storiesAdapter = StoryAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = storiesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}