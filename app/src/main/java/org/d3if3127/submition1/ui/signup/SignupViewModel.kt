package org.d3if3127.submition1.ui.signup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import org.d3if3127.submition1.data.repository.UserRepository
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return repository.register(name, email, password)
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val getLocation : LiveData<StoryResponse> = liveData(viewModelScope.coroutineContext) {
        val storyResponse = repository.getStoriesWithLocation()
        emit(storyResponse)
    }
}