package org.d3if3127.submition1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3127.submition1.data.repository.UserRepository
import org.d3if3127.submition1.data.response.DetailResponse

class DetailViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getDetail(id: String): DetailResponse {
        _isLoading.value = true
        return repository.getDetail(id)
    }




}