package com.regiscorp.myapplication.hilt


import com.regiscorp.myapplication.constants.BASE_GITHUB_URL
import com.regiscorp.myapplication.endpoint.WebEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWebService(): WebEndpoint {
        val httpClient = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(BASE_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(WebEndpoint::class.java)
    }
}