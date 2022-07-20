package com.frndzcode.client.example_room_hilt.data.network

import android.content.Context
import com.frndzcode.client.example_room_hilt.BuildConfig
import com.frndzcode.client.example_room_hilt.app.MyConstants.API_CONST.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import java.util.concurrent.TimeUnit

class RemoteDataSource @Inject constructor() {

    companion object {
       // private const val BASE_URL = BASE_URL
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient(context))
            //.client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)

    }


    private fun getRetrofitClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            /**
             * timeout retrofit connection
             *
             * .connectTimeout(60, TimeUnit.SECONDS)
             * .readTimeout(60, TimeUnit.SECONDS)
             * .writeTimeout(60, TimeUnit.SECONDS)

             * end **/
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
//                    it.addHeader("Authorizationkey", PrefUtils.getAccessToken(context))
//                    println("I am access token: ${PrefUtils.getAccessToken(context)}")
                }.build())
            }.also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}