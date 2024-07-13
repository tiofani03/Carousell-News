package com.tiooooo.newstest.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.core.constant.Constants
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.network.data.States
import com.tiooooo.core.network.data.handleStates
import com.tiooooo.newstest.R
import com.tiooooo.newstest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        observeData()

        binding.swipeRefresh.setOnRefreshListener {
            getData(isRefresh = true)
            binding.swipeRefresh.isRefreshing = false
        }

        setSupportActionBar(binding.toolbar)
    }

    private fun getData(
        isRefresh: Boolean = false,
        isSortByRank: Boolean = false,
        isSortByLatest: Boolean = false
    ) {
        mainViewModel.getNews(
            isRefresh = isRefresh,
            isSortByRank = isSortByRank,
            isSortByLatest = isSortByLatest,
        )
    }

    private fun observeData() {
        collectFlow(mainViewModel.news) { state ->
            binding.progressBar.root.isVisible = state is States.Loading
            binding.rvNews.isVisible = state is States.Success
            binding.emptyState.root.isVisible = state is States.Empty || state is States.Error

            state.handleStates(
                loadingBlock = {},
                successBlock = {
                    val newsAdapter = NewsAdapter(it)
                    binding.rvNews.apply {
                        adapter = newsAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                },
                emptyBlock = {
                    binding.emptyState.tvInfo.text = getString(R.string.empty_state_message)
                },
                errorBlock = {
                    val errorMessage = when (it) {
                        Constants.ERROR_CODE_NO_INTERNET -> getString(R.string.error_no_internet)
                        Constants.ERROR_DEFAULT -> getString(R.string.error_default)
                        else -> it
                    }
                    binding.emptyState.tvInfo.text = errorMessage
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSortByRating -> getData(isSortByRank = true)
            R.id.menuSortByLatest -> getData(isSortByLatest = true)
        }
        return super.onOptionsItemSelected(item)
    }
}