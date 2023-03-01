package com.example.invoices.di

import com.example.invoices.data.remote.apis.InvoicesApi
import com.example.invoices.data.repositories.InvoiceRepository
import com.example.invoices.data.repositories.InvoiceRepositoryImpl
import com.example.invoices.utils.retrofit.ResultAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun proveOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(ResultAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://storage.googleapis.com").build()
    }

    @Provides
    @Singleton
    fun provideInvoicesApi(retrofit: Retrofit): InvoicesApi {
        return retrofit.create(InvoicesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideInvoiceRepository(
        api: InvoicesApi,
        @ApplicationScope scope: CoroutineScope
    ): InvoiceRepository {
        return InvoiceRepositoryImpl(
            invoiceApiDataSource = api,
            externalScope = scope
        )
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}