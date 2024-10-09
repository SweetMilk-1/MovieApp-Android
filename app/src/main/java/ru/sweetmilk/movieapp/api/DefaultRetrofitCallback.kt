package ru.sweetmilk.movieapp.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultRetrofitCallback<T>(
    private val logTag: String,
    private val responseLiveData: MutableLiveData<T>? = null,
    private val callbacks: HttpCallbacks? = null
)  : Callback<T> {
    override fun onResponse(request: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            responseLiveData?.value = response.body()!!
            Log.i(logTag, "Data Loading from ${request.request().url()} was successful")
            callbacks?.onFinishLoading()
        }
        else {
            Log.w(logTag, "Data Loading from ${request.request().url()} has errors")
            callbacks?.onRequestFailure(response.toErrorResponse())
        }
    }

    override fun onFailure(request: Call<T>, exception: Throwable) {
        Log.e(logTag, "Could not loading data in MovieRepository", exception)
        callbacks?.onError()
    }
}