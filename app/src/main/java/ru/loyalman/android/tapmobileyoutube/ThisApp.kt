package ru.loyalman.android.tapmobileyoutube

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.loyalman.android.base.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class ThisApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}