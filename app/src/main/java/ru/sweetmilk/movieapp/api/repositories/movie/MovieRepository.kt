package ru.sweetmilk.movieapp.api.repositories.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.sweetmilk.movieapp.api.DefaultRetrofitCallback
import ru.sweetmilk.movieapp.api.HttpCallbacks
import ru.sweetmilk.movieapp.api.RetrofitFactory
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse

private val LOG_TAG = "MovieRepository"
class MovieRepository {
    var callbacks: HttpCallbacks? = null

    private val retrofit = RetrofitFactory.create()
    private var movieApi: MovieApi = retrofit.create(MovieApi::class.java)

    fun getMoviesList(): LiveData<PagedResponse<MovieListItem>> {
        val moviesListLiveData = MutableLiveData<PagedResponse<MovieListItem>>()
        callbacks?.onStartLoading()
        movieApi.getMovieList()
            .enqueue(DefaultRetrofitCallback(LOG_TAG, moviesListLiveData, callbacks))
        return moviesListLiveData
    }

    suspend fun getMovieImage(id: String): Bitmap = withContext(Dispatchers.IO) {
        val responseBody = movieApi.getMovieImage(id)
        BitmapFactory.decodeStream(responseBody.byteStream());
    }
}