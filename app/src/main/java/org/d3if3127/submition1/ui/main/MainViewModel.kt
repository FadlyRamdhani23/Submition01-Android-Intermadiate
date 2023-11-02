package org.d3if3127.submition1.ui.main


import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.repository.UserRepository
import org.d3if3127.submition1.data.response.ListStoryItem


class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

    val stories: LiveData<PagingData<ListStoryItem>> =
        repository.getStory().cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}