package org.d3if3127.submition1.di

import android.content.Context
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.preference.dataStore
import org.d3if3127.submition1.data.repository.UserRepository
import org.d3if3127.submition1.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}