package com.luis.android.themoviechallenge.domain.usecase

import com.luis.android.themoviechallenge.data.repository.MovieRepositoryImp
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val movieRepositoryImp: MovieRepositoryImp) {


    fun uploadImage(imagePath:String) {
        movieRepositoryImp.uploadImage(imagePath)
    }

}