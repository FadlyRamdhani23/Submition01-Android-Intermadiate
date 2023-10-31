package org.d3if3127.submition1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if3127.submition1.R
import org.d3if3127.submition1.databinding.ActivityMainBinding
import org.d3if3127.submition1.ui.adapter.StoryAdapter
import org.d3if3127.submition1.ui.upload.UploadStoryActivity
import org.d3if3127.submition1.ui.factory.ViewModelFactory
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

    private fun actionSet() {
        viewModel.stories.observe(this) { storyResponse ->
            try {
                val stories = storyResponse?.listStory ?: emptyList()
                storiesAdapter.submitList(stories)
                ViewModelFactory.refreshInstance()
                showLoading(false)
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        storiesAdapter = StoryAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = storiesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.uploadButton.setOnClickListener {
            viewModel.getSession().observe(this) { user ->
                val moveWithObjectIntent = Intent(this, UploadStoryActivity::class.java)
                moveWithObjectIntent.putExtra(UploadStoryActivity.GITHUB_TOKEN, user.token)
                startActivity(moveWithObjectIntent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLogout -> {
                // Menangani tindakan logout di sini
                viewModel.logout()
                ViewModelFactory.refreshInstance()
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}