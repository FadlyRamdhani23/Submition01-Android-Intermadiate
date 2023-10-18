package org.d3if3127.submition1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.d3if3127.submition1.R
import org.d3if3127.submition1.data.response.DetailResponse
import org.d3if3127.submition1.data.response.Story
import org.d3if3127.submition1.databinding.ActivityDetailBinding
import org.d3if3127.submition1.ui.ViewModelFactory
import org.d3if3127.submition1.ui.main.MainViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        val id = intent.getStringExtra(GITHUB_ID) // Use getStringExtra for a String value

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Check if id is not null before using it
        id?.let { nonNullId ->
            lifecycleScope.launch {
                val detailResponse = viewModel.getDetail(nonNullId)
                setDetailScreen(detailResponse)
            }
        }
    }
    private fun setToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDetailScreen(detailResponse: DetailResponse?) {
        val detail: Story? = detailResponse?.story

        binding.apply {
            name.text = detail?.name
            description.text = detail?.description
            Glide.with(this@DetailActivity)
                .load(detail?.photoUrl)
                .into(photo)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarr.isVisible = isLoading
    }
    companion object {
        const val GITHUB_ID = "github_id"
    }
}