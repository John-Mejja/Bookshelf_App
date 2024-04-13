package com.example.bookshelfapp.data.network

import com.example.bookshelfapp.model.Item
import com.example.bookshelfapp.model.VolumesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleApisBookService {
    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): Response<VolumesResponse>

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Response<Item>
}