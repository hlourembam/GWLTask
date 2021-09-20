package com.herojit.gwltask.models

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

import com.google.gson.JsonObject


interface APIServiceProvider {

//    @Headers("Content-Type: text/html")
    @GET("{URL}")
    fun getStringAPI(
        @Path("URL") URL: String
    ): Call<JsonObject>


    companion object {

        operator fun invoke(): APIServiceProvider {
            val interceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(APIUrls.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIServiceProvider::class.java)

        }

    }
}