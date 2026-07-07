package com.schoolmanagement.mobile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.schoolmanagement.mobile.data.api.SchoolApi
import com.schoolmanagement.mobile.data.repository.SchoolRepository
import com.schoolmanagement.mobile.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.AUTH_PREFERENCES)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.authDataStore

    @Provides
    @Singleton
    fun provideAuthInterceptor(dataStore: DataStore<Preferences>): Interceptor =
        Interceptor { chain ->
            val token = java.util.concurrent.atomic.AtomicReference<String?>(null)
            kotlinx.coroutines.runBlocking {
                token.set(dataStore.data.first()[androidx.datastore.preferences.core.stringPreferencesKey(Constants.AUTH_TOKEN_KEY)])
            }
            val request = if (token.get().isNullOrBlank()) {
                chain.request()
            } else {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${token.get()}")
                    .build()
            }
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSchoolApi(retrofit: Retrofit): SchoolApi = retrofit.create(SchoolApi::class.java)

    @Provides
    @Singleton
    fun provideSchoolRepository(
        api: SchoolApi,
        dataStore: DataStore<Preferences>
    ): SchoolRepository = SchoolRepository(api, dataStore)
}
