package com.tiooooo.newstest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.tiooooo.data.news.api.model.News
import com.tiooooo.newstest.R
import com.tiooooo.newstest.databinding.ItemNewsBinding

class NewsAdapter(
    private val list: List<News>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(news: News) {
            binding.apply {
                tvTitle.text = news.title
                tvDesc.text = news.description
                tvTimeAgo.text = TimeAgo.using(news.timeCreated)
                ivContent.load(news.bannerUrl) {
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                    crossfade(true)
                }
            }
        }

    }
}