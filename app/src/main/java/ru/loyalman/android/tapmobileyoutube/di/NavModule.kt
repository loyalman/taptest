package ru.loyalman.android.tapmobileyoutube.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.loyalman.android.base.BaseNavigation
import ru.loyalman.android.tapmobileyoutube.RootNavigation

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class NavModule {


    @ActivityRetainedScoped
    @Binds
    abstract fun bindNavigation(mpl: RootNavigation): BaseNavigation
}