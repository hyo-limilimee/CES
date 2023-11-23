package com.ssu.bilda.data.remote

import android.util.Log
import okhttp3.Interceptor
import com.ssu.bilda.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {

    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "CONNECTION INFO -> $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLogOkHttpClient())
        .build()

    fun retrofit(url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

//    inline fun <reified T, B> create(url: B): T =
//        retrofit(url.toString()).create(T::class.java)
}

//object ServicePool {
//    val authService = RetrofitClass.create<AuthService, String>(AUTH_BASE_URL)
//    val userService = RetrofitClass.create<UserService, String>(AUTH_BASE_URL)
//    val apiService = RetrofitClass.create<PeopleService, String>(OPEN_BASE_URL)
//}
