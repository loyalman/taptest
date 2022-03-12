package ru.loyalman.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseFragment<T : ViewBinding, S : BaseEvent> : Fragment() {
    abstract val viewModel: BaseViewModel<S>
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    protected var _binding: T? = null
    protected open val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.oneTimeEvents.collectLatest(::handleEvents)
        }
    }

    abstract fun handleEvents(screenEvent: S)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
        }

    fun launchCoroutine(whatToDo: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch(
            context = exceptionHandler,
            block = whatToDo
        )
    }
}
