package br.com.digitalhouse.desafio03dh.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.desafio03dh.model.ComicResponse
import br.com.digitalhouse.desafio03dh.service.ComicService
import br.com.digitalhouse.desafio03dh.service.RetrofitBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComicViewModel(comicService: ComicService, application: Application) : AndroidViewModel(application) {
    val comic = MutableLiveData<ComicResponse>()
    private val context = getApplication<Application>().applicationContext
    fun getComic(comicId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitBuilder.getService()!!.getComic(comicId)
                val results = response.get("data")
                val comic = Gson().fromJson(
                    results,
                    object : TypeToken<ComicResponse>() {}.type
                ) as ComicResponse
                comic.value = comic
            } catch (e: Exception) {
                Log.e(this.javaClass.name, e.toString())
                Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show()
            }
        }

    }
}