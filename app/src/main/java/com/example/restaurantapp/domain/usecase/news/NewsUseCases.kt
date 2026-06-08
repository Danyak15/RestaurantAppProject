package com.example.restaurantapp.domain.usecase.news

import com.example.restaurantapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(restaurantId: Long? = null) =
        newsRepository.getNews(restaurantId)
}
