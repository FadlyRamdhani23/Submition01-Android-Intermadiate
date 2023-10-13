package org.d3if3127.submition1.di

import android.content.Context
import org.d3if3127.submition1.data.preference.UserPreference
import org.d3if3127.submition1.data.preference.dataStore
import org.d3if3127.submition1.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}