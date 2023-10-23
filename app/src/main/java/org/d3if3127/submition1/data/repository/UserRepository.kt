package org.d3if3127.submition1.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.response.DetailResponse
import org.d3if3127.submition1.data.response.FileUploadResponse
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.data.retrofit.ApiService
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference)
{
    private val apiService: ApiService = ApiConfig.getApiService()

    private suspend fun saveToken(token: String) {
        userPreference.saveToken(token)
        Log.d("Login", token)
    }

    private suspend fun getToken(): String {
        return userPreference.getSession().first().token
    }
    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }
    suspend fun getStories(): StoryResponse {
        try {
            return apiService.getStories("Bearer " + getToken())
        } catch (e: HttpException) {
            if (e.code() == 401) {
                throw Exception("Token tidak valid")
            } else {
                // Handle other exceptions
                throw e
            }
        }
    }
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String) {
        val loginResponse = apiService.login(email, password)
        saveToken(loginResponse.loginResult?.token ?: "")
    }

    suspend fun uploadImage(file: MultipartBody.Part, description: String ): FileUploadResponse {
        return apiService.uploadImage("Bearer " + getToken(),file, description)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getDetail(id: String): DetailResponse {
        return apiService.getDetailStory("Bearer " + getToken(),id)
    }



    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}