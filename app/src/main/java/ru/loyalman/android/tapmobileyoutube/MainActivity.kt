package ru.loyalman.android.tapmobileyoutube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.loyalman.android.base.BaseNavigation
import ru.loyalman.android.tapmobileyoutube.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        findNavController(R.id.navHostFragment)
    }

    @Inject
    lateinit var navigation: BaseNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation.bindController(navController)
    }

    override fun onDestroy() {
        navigation.unbindController()
        super.onDestroy()
    }
}