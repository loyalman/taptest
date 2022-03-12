package ru.loyalman.android.tapmobileyoutube.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.loyalman.android.base.BaseFragment
import ru.loyalman.android.tapmobileyoutube.databinding.MainFragmentBinding
import ru.loyalman.android.tapmobileyoutube.ui.adapters.SearchAdapter

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding, MainEvent>() {

    override val viewModel by viewModels<MainViewModel>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(
            onPlay = {
                //didn't have tome to make it embedded
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                val chooser = Intent.createChooser(intent, "Open with...")
                try {
                    startActivity(chooser)
                } catch (e: ActivityNotFoundException) {
                    //show some
                }
            }
        )
    }

    override fun handleEvents(screenEvent: MainEvent) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.create()
        binding.resultList.adapter = searchAdapter
        binding.searchField.addTextChangedListener {
            viewModel.search(it?.toString() ?: "")
        }
        lifecycleScope.launchWhenStarted {
            viewModel.result.collect {
                searchAdapter.submitList(it)
            }
        }
    }

}