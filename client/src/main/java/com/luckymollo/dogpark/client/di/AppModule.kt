package com.luckymollo.dogpark.client.di

import android.content.Context
import com.google.gson.Gson
import com.luckymollo.dogpark.client.BuildConfig
import com.luckymollo.dogpark.client.data.Repository
import com.luckymollo.dogpark.client.data.ClientService
import com.luckymollo.dogpark.core.data.AuthInterceptor
import com.luckymollo.dogpark.core.data.CoreDatabaseManager
import com.luckymollo.dogpark.core.data.CoreService
import com.luckymollo.dogpark.core.session.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules: List<Module> by lazy { listOf(appModule, daoModule, networkModule) }

val appModule = module {
    single { get<Retrofit>().create(ClientService::class.java) }
    single { get<Retrofit>().create(CoreService::class.java) }
    single { Repository(get(), get()) }
}

val networkModule = module {
    single { provideDefaultOkhttpClient(get(), get()) }
    single { provideRetrofit(get()) }
}

val daoModule = module {
    single { CoreDatabaseManager.buildDatabase(get()) }
    single { get<CoreDatabaseManager>().sessionDao }
    single { SessionManager(get()) }
}

private fun provideDefaultOkhttpClient(context: Context, sessionManager: SessionManager): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(AuthInterceptor(context, sessionManager))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE })
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl("https://test.io/")
        .client(okHttpClient)
        .build()
}
