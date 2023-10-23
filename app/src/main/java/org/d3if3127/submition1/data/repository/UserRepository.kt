package org.d3if3127.submition1.data.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.response.DetailResponse
import org.d3if3127.submition1.data.response.FileUploadResponse
import org.d3if3127.submition1.data.response.LoginResult
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse
import org.d3if3127.submition1.data.retrofit.ApiService

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    private suspend fun saveToken(token: String) {
        userPreference.saveToken(token)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }
    suspend fun getStories(): StoryResponse {
        return apiService.getStories()
    }
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)

    }

    suspend fun login(email: String, password: String): LoginResult {
        try {
            val loginResponse = apiService.login(email, password)
            val loginResult = loginResponse.loginResult

            if (loginResult != null) {
                saveToken(loginResult.token)
                return loginResult
            } else {
                userPreference.logout()
                throw Exception(loginResponse.message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadImage(file: MultipartBody.Part, description: String ): FileUploadResponse {
        return apiService.uploadImage(file, description)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getDetail(id: String): DetailResponse {
        return apiService.getDetailStory(id)
    }



    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}