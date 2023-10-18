package com.luis.android.themoviechallenge.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.android.themoviechallenge.domain.usecase.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    fun uploadImage(imagePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadImageUseCase.uploadImage(imagePath)
        }
    }


}