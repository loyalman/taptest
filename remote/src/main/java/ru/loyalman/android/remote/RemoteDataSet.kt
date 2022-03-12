package ru.loyalman.android.remote

import ru.loyalman.android.remote.dto.VideoResponse

interface RemoteDataSet {
    suspend fun getSearchResult(query: String): List<VideoResponse>
}