package ru.loyalman.android.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.loyalman.android.remote.RemoteDataSet
import ru.loyalman.android.remote.RemoteDataSetImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSetModule {

    @Binds
    abstract fun bindDataSet(impl: RemoteDataSetImpl): RemoteDataSet
}