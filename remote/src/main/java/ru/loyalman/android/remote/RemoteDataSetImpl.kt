package ru.loyalman.android.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.loyalman.android.remote.di.Page
import ru.loyalman.android.remote.dto.VideoResponse
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RemoteDataSetImpl @Inject constructor(
    private val networkApi: NetworkApi,
) : RemoteDataSet {
    override suspend fun getSearchResult(query: String): List<VideoResponse> {
        return suspendCoroutine {
            networkApi.searchYoutube(query).enqueue(object : Callback<Page?> {
                override fun onResponse(call: Call<Page?>, response: Response<Page?>) {
                    val page = response.body()
                    if (page == null) {
                        it.resume(emptyList())
                        return
                    }
                    val videos = mutableListOf<VideoResponse>()
                    val patternVideo =
                        Pattern.compile("\"videoRenderer\":\\{\"videoId\":\"([\\w]+)\",\"thumbnail\":\\{\"thumbnails\":\\[\\{\"url\":\"([^\"]+)\"")
                    val matcherVideo = patternVideo.matcher(page.content)
                    while (matcherVideo.find()) {
                        matcherVideo.takeIf { it.groupCount() == 2 }?.let {
                            videos.add(
                                VideoResponse(
                                    "",
                                    matcherVideo.group(1).orEmpty(),
                                    matcherVideo.group(2).orEmpty()
                                )
                            )
                        }
                    }
                    it.resume(videos)
                }

                override fun onFailure(call: Call<Page?>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}