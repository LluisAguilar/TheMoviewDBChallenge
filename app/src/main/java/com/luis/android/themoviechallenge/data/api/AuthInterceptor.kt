package com.luis.android.themoviechallenge.data.api

import com.luis.android.themoviechallenge.data.helpers.StringDataCommonHelper.Companion.AUTHORIZATION_API_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val requestBuilder = request.newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer +$AUTHORIZATION_API_TOKEN")
        requestBuilder.addHeader("Content-Type", "application/json;charset=utf-8'")

        request = requestBuilder.build()

        return chain.proceed(request)
    }
}