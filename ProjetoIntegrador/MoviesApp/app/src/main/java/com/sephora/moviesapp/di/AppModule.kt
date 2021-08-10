package com.sephora.moviesapp.di

import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.model.Credentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

        @Provides
        @Singleton
        fun provideRetrofit() : Retrofit =

            Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Provides
        @Singleton
        fun getService (retrofit : Retrofit): TmdbService {

            return retrofit.create((TmdbService::class.java))
        }

}