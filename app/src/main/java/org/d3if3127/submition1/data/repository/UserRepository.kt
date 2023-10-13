package org.d3if3127.submition1.data.repository

import kotlinx.coroutines.flow.Flow
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.response.LoginResponse
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.retrofit.ApiService

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveToken(token: String) {
        userPreference.saveToken(token)
    }

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
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