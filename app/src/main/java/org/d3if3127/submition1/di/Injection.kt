package org.d3if3127.submition1.di

import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.preference.dataStore
import org.d3if3127.submition1.data.repository.UserRepository
import org.d3if3127.submition1.data.retrofit.ApiConfig

object Injection {
     fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)

        val token = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(token.token)

        return UserRepository.getInstance(pref, apiService)
    }
    fun refreshRepository() {
        UserRepository.refreshInstance()
    }
}