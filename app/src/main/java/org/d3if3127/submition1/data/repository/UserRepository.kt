package org.d3if3127.submition1.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.response.DetailResponse
import org.d3if3127.submition1.data.response.ListStoryItem
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    suspend fun login(email: String, password: String) {
        val loginResponse = apiService.login(email, password)
        loginResponse.loginResult?.token?.let { saveToken(it) }
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