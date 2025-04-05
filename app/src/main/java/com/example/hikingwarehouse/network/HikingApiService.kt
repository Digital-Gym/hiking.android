package com.example.hikingwarehouse.network

import com.example.hikingwarehouse.model.BaseResponse
import com.example.hikingwarehouse.model.ItemResponse
import retrofit2.Retrofit
import retrofit2.http.GET

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request

private const val BASE_URL = "https://wiutmadcw.uz/api/v1/"
private const val STUDENT_ID = "00015641"

val contentType = "application/json".toMediaType()

// interceptor logic
val studentIdInterceptor = Interceptor { chain ->
    val originalRequest: Request = chain.request()

    val url: HttpUrl = originalRequest.url.newBuilder()
        .addQueryParameter("student_id", STUDENT_ID)
        .build()

    val newRequest: Request = originalRequest.newBuilder()
        .url(url)
        .build()

    chain.proceed(newRequest)
}

val client = OkHttpClient.Builder()
    .addInterceptor(studentIdInterceptor)  // Add the interceptor
    .build()
//---


private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(contentType))
    .baseUrl(BASE_URL)
    .client(client)
    .build()



object HikingApi {
    val retrofitService : HikingApiService by lazy {
        retrofit.create(HikingApiService::class.java)
    }
}


// destinations
interface HikingApiService {
    @GET("records/all")
    suspend fun getAllItems(): BaseResponse<List<ItemResponse>>
}
