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

class MainViewModel(comicResponse: ComicService, application: Application) :
    AndroidViewModel(application) {

    val comicList = MutableLiveData<ComicResponse>()
    private val context = getApplication<Application>().applicationContext

    fun getComics(offset: Int = 0) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitBuilder.getService()!!.getHQs(offset)
                val results = response.get("data")
                val comics = Gson().fromJson(
                    results,
                    object : TypeToken<ComicResponse>() {}.type
                ) as ComicResponse
                comicList.value = comics
            } catch (e: Exception) {
                Log.w("MainViewModel", e.toString())
                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
            }

        }
    }
}