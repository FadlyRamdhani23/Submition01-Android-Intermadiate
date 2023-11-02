package org.d3if3127.submition1.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

import org.d3if3127.submition1.data.StoryRemoteMediator
import org.d3if3127.submition1.data.database.StoryDatabase
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.preference.UserPreference

import org.d3if3127.submition1.data.response.FileUploadResponse
import org.d3if3127.submition1.data.response.ListStoryItem
import org.d3if3127.submition1.data.response.LoginResult
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse
import org.d3if3127.submition1.data.retrofit.ApiService

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val StoryDatabase: StoryDatabase
) {

    private suspend fun saveToken(token: String) {
        userPreference.saveToken(token)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
            pageSize = 5
        ),
        remoteMediator = StoryRemoteMediator(StoryDatabase, apiService),
        pagingSourceFactory = {
            StoryDatabase.quoteDao().getAllQuote()
        }
    ).liveData
}
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)

    }
    suspend fun getStoriesWithLocation(): StoryResponse {
        return apiService.getStoriesWithLocation()
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

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
            database: StoryDatabase
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService, database)
            }.also { instance = it }
        fun refreshInstance() {
            instance = null
        }
    }
}