package com.luis.android.themoviechallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.android.themoviechallenge.domain.usecase.UploadImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel @ViewModelInject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    fun uploadImage(imagePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadImageUseCase.uploadImage(imagePath)
        }
    }


}