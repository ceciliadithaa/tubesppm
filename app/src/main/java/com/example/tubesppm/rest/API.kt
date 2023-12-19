package com.example.tubesppm.rest

import com.example.tubesppm.data.*
import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("read.php")
    fun getBooks():Call<ArrayList<BookItem>>

    @GET("detail.php")
    fun getBookDetail(
        @Query("bookId") countryId: String?
    ):Call<BookDetail>

    @FormUrlEncoded
    @POST("add.php")
    fun addBookDetail(
        @Field("book_name") book_name: String?,
        @Field("book_writer") book_writer: String?,
        @Field("book_year") book_year: String?,
        @Field("book_summary") book_summary: String?
    ): Call<Response>

    @FormUrlEncoded
    @POST("update.php")
    fun updateBookDetail(
        @Field("book_id") book_id: String?,
        @Field("book_name") book_name: String?,
        @Field("book_writer") book_writer: String?,
        @Field("book_year") book_year: String?,
        @Field("book_summary") book_summary: String?
    ): Call<Response>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteBookDetail(
        @Field("book_id") book_id: String?
    ): Call<Response>
}