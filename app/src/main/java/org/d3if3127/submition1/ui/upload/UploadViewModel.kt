package org.d3if3127.submition1.ui.upload

import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import org.d3if3127.submition1.data.repository.UserRepository

class UploadViewModel(private val repository: UserRepository) : ViewModel() {
        suspend fun uploadImage(file: MultipartBody.Part, description: String ) {
            repository.uploadImage(file ,description)
        }
}
