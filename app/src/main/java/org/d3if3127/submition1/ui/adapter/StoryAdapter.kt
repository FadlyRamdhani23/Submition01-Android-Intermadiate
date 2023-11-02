package org.d3if3127.submition1.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3127.submition1.data.response.ListStoryItem

import org.d3if3127.submition1.databinding.ItemStoryBinding
import org.d3if3127.submition1.ui.detail.DetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val moveWithObjectIntent = Intent(context, DetailActivity::class.java)
            moveWithObjectIntent.putExtra(DetailActivity.GITHUB_ID, story)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    androidx.core.util.Pair(holder.binding.photo, "profile"),
                    androidx.core.util.Pair(holder.binding.name, "name"),
                    androidx.core.util.Pair(holder.binding.description, "description")
                )
            context.startActivity(moveWithObjectIntent, optionsCompat.toBundle())
        }

        story?.let { nonNullStory ->
            holder.bind(nonNullStory)
        }
    }


    class MyViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.name.text = "${story.name}"
            binding.description.text = "${story.description}"
            Glide.with(binding.root.context)
                .load("${story.photoUrl}")
                .into(binding.photo)

        }

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}