package com.ergophile.yd_notes.di

import com.ergophile.yd_notes.data.source.remote.ApiInterface
import com.ergophile.yd_notes.data.source.repository.YDNotesRepository
import com.ergophile.yd_notes.data.source.repository.YDNotesRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppModule {
    val getApiSupabase: ApiInterface
    val getRepository: YDNotesRepository
}

class AppModuleImpl : AppModule {
    override val getApiSupabase: ApiInterface by lazy {

        val client = OkHttpClient.Builder().addInterceptor(OkHttpInterceptor())

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(ApiInterface::class.java)
    }

    override val getRepository: YDNotesRepository by lazy {
        YDNotesRepositoryImpl(getApiSupabase)
    }

    companion object {
        private const val BASE_URL = "https://rxzynfxgsskvhtqyjbex.supabase.co"
    }

}

//Add Interceptor
class OkHttpInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enluZnhnc3Nrdmh0cXlqYmV4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5NDU5MzgsImV4cCI6MjAzMjUyMTkzOH0.lTzgX8s5kVAumJbf6YdYI-8UzbEsTGOkFjSBfuyjNSc")
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enluZnhnc3Nrdmh0cXlqYmV4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5NDU5MzgsImV4cCI6MjAzMjUyMTkzOH0.lTzgX8s5kVAumJbf6YdYI-8UzbEsTGOkFjSBfuyjNSc")
            .build()
        return chain.proceed(request = request)
    }
}