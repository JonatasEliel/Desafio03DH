package br.com.digitalhouse.desafio03dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.digitalhouse.desafio03dh.R
import br.com.digitalhouse.desafio03dh.adapter.AdapterComic
import br.com.digitalhouse.desafio03dh.adapter.OnClickListenerItem
import br.com.digitalhouse.desafio03dh.service.RetrofitBuilder
import br.com.digitalhouse.desafio03dh.model.*
import br.com.digitalhouse.desafio03dh.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickListenerItem {
    var comicList = ArrayList<Result>()
    private lateinit var adapter: AdapterComic
    var offset = 0
    val limit = 20

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(RetrofitBuilder.getService()!!, application) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.marvel_logo_actionbar)
        supportActionBar?.setTitle("")

        adapter = AdapterComic(comicList, this)
        homeRVComic.adapter = adapter

        homePBLoading.visibility = View.VISIBLE

        viewModel.getComics()

        viewModel.comicList.observe(this) {
            comicList.addAll(it.results)

            adapter = AdapterComic(comicList, this)
            homeRVComic.adapter = adapter

            homePBLoading.visibility = View.INVISIBLE
        }

        homeSVFrame.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    offset += limit
                    homePBLoading.visibility = View.VISIBLE
                    viewModel.getComics(offset)
                }
            }
        )
    }

    override fun OnClickItem(position: Int) {
        val comic = comicList[position]
        val intent = Intent(this, ComicActivity::class.java)

        intent.putExtra("comic", comic.id)
        startActivity(intent)
    }
}