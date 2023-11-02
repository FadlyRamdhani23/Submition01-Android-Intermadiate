package org.d3if3127.submition1.data.retrofit

import okhttp3.MultipartBody
import org.d3if3127.submition1.data.response.FileUploadResponse
import org.d3if3127.submition1.data.response.LoginResponse
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: String,
    ): FileUploadResponse
    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): StoryResponse

}