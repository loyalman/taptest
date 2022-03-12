package ru.loyalman.android.tapmobileyoutube.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.loyalman.android.base.BaseViewModel
import ru.loyalman.android.remote.RemoteDataSet
import ru.loyalman.android.remote.dto.VideoResponse
import ru.loyalman.android.tapmobileyoutube.ui.adapters.ResultItem
import ru.loyalman.android.tapmobileyoutube.ui.adapters.toViewItem
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteData: RemoteDataSet,
) : BaseViewModel<MainEvent>() {

    private val _result: MutableStateFlow<List<ResultItem>> = MutableStateFlow(emptyList())
    val result: StateFlow<List<ResultItem>> = _result

    private val _search: MutableStateFlow<String> = MutableStateFlow("")


    override val errorHandler: (Throwable) -> Unit = {
        _oneTimeEvents.tryEmit(MainEvent.Error(it))
    }

    fun create() = launchCatching {
        _search.debounce(500)
            .filter { it.isNotEmpty() }
            .collect {
                val result = remoteData.getSearchResult(query = it)
                _result.emit(result.map(VideoResponse::toViewItem))
            }
    }

    fun search(query: String) = launchCatching {
        _search.emit(query)
    }
}