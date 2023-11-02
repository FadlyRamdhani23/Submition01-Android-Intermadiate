package org.d3if3127.submition1.ui.maps


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import org.d3if3127.submition1.data.repository.UserRepository
import org.d3if3127.submition1.data.response.StoryResponse

class MapViewModel(private val repository: UserRepository) : ViewModel() {

    val getLocation : LiveData<StoryResponse> = liveData(viewModelScope.coroutineContext) {
        val storyResponse = repository.getStoriesWithLocation()
        emit(storyResponse)
    }
}