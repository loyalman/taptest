package ru.loyalman.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

abstract class BaseViewModel<T : BaseEvent> : ViewModel() {

    @Inject
    lateinit var vmNavigation: BaseNavigation

    val viewModelErrorHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            errorHandler(throwable)
        }

    abstract val errorHandler: (Throwable) -> Unit

    fun <T> launchCatching(block: suspend CoroutineScope.() -> T) {
        viewModelScope.launch(viewModelErrorHandler) { block() }
    }

    fun <T> launchNoCatching(block: suspend CoroutineScope.() -> T) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Timber.e("ignore exception")
                Timber.e(throwable)
            }
        ) { block() }
    }

    val oneTimeEvents: SharedFlow<T>
        get() = _oneTimeEvents
    protected val _oneTimeEvents = MutableSharedFlow<T>()
}
