package org.d3if3127.submition1.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if3127.submition1.data.model.User
import org.d3if3127.submition1.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}