package com.luis.android.themoviechallenge.data.repository

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.luis.android.themoviechallenge.domain.helper.ResultWrapper
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


class RemoteDataSource {

    suspend fun <T> getUserProfileResponse(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {

        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        ResultWrapper.RequestError(0, throwable.message)
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.RequestError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.RequestError(0, null)
                    }
                }
            }
        }

    }

    private fun convertErrorBody(throwable: HttpException): String {
        return throwable.message() ?: "Error not found"
    }

    fun uploadImageFile(
        imageUri: String
    ) {
        val filePath = FirebaseStorage.getInstance().getReference("Posts")
            .child(System.currentTimeMillis().toString() + "." + imageUri)

        filePath.putFile(imageUri.toUri())
            .addOnCompleteListener {}

    }


}