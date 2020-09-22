package org.d3if4048.thewallstreetjournal.home

import androidx.lifecycle.ViewModel
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository

class HomeViewModel(newsRepository: NewsRepository) : ViewModel() {
    val news =newsRepository.getAllNews()
}