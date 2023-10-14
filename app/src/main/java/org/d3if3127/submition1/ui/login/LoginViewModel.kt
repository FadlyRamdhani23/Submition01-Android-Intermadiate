package org.d3if3127.submition1.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun login(email: String, password: String) {
        repository.login(email, password)
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
}
