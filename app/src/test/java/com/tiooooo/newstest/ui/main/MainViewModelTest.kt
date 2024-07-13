package com.tiooooo.newstest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.tiooooo.core.network.data.States
import com.tiooooo.data.news.api.repository.NewsRepository
import com.tiooooo.newstest.helper.CoroutineTestRule
import com.tiooooo.newstest.helper.generateListNews
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    var dispatcherRule = CoroutineTestRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var newsRepository: NewsRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            newsRepository = newsRepository,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get news from api`() = runTest {

        val genreList = generateListNews()

        coEvery {
            newsRepository.getNews()
        } returns flow {
            emit(States.Loading)
            emit(States.Success(genreList))
        }

        viewModel.news.test {
            viewModel.getNews()
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }
}