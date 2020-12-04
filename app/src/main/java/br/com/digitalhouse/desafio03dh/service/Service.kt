package br.com.digitalhouse.desafio03dh.service

import android.util.Log
import android.widget.Toast
import br.com.digitalhouse.desafio03dh.interceptor.MarvelApiInterceptor
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicService {
    @GET("comics")
    suspend fun getHQs(@Query("offset") offset: Int): JsonObject

    @GET("comics/{id}")
    suspend fun getHQ(@Path("id") comicId: Int): JsonObject
}

class RetrofitBuilder() {
    companion object {
        fun getService(): ComicService? {
            try {
                val client = OkHttpClient()
                    .newBuilder()
                    .addInterceptor(MarvelApiInterceptor())
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                return retrofit.create(ComicService::class.java)
            } catch (e: Exception) {
                Log.i("RetrofitBuilder", e.message.toString())
                Toast.makeText(null, "Failed to connect", Toast.LENGTH_LONG).show()
                return null
            }
        }
    }
}