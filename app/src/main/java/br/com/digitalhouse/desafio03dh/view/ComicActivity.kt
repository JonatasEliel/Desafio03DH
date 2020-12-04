package br.com.digitalhouse.desafio03dh.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.digitalhouse.desafio03dh.R
import br.com.digitalhouse.desafio03dh.model.ComicResponse
import br.com.digitalhouse.desafio03dh.service.RetrofitBuilder
import br.com.digitalhouse.desafio03dh.viewModel.ComicViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_comic.*
import java.text.SimpleDateFormat
import java.util.*

class ComicActivity : AppCompatActivity() {
    private lateinit var comic: ComicResponse

    private val viewModel by viewModels<ComicViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ComicViewModel(RetrofitBuilder.getService()!!, application) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        setUpOnClickListeners()

        supportActionBar?.hide()

        val comicId: Int = intent.getIntExtra("comic", 0).toInt()

        if (comicId == 0) {
            Toast.makeText(this, "Comic not found", Toast.LENGTH_LONG).show()
            onBackPressed()
        }

        try {
            viewModel.getComic(comicId)
        } catch (e: Exception) {
            Log.i(this.javaClass.name, e.message.toString())
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_LONG).show()
        }

        viewModel.comic.observe(this) {
            comic = it
            val comic = comic.results[0]
            comicTVTitle.text = comic.title

            comicTVDescription.text = comic.description

            if (comic.pageCount != 0)
                comicTVPages.text = comic.pageCount.toString()
            else comicTVPages.text = "No data"

            if (comic.prices[0].price != 0.0)
                comicTVPrice.text = comic.prices[0].price.toString()
            else comicTVPrice.text = "No data"

            val jsDate = (comic.dates.find { s -> s.type == "focDate" })?.date.toString()
            if (jsDate.toCharArray()[0] != '-') {
                val javaDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(jsDate)
                val dateFmt = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
                val date = dateFmt.format(javaDate)
                comicTVPublished.text = date
            } else comicTVPublished.text = "No data"

            Glide.with(this)
                .load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fallback(R.drawable.ic_baseline_image_not_supported_24)
                .into(comicIVCover)

            Glide.with(this)
                .load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fallback(R.drawable.ic_baseline_image_not_supported_24)
                .into(comicIVWallpaper)
        }
    }

    private fun setUpOnClickListeners(){
        comicIVBack.setOnClickListener { onBackPressed() }
    }
}