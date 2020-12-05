package br.com.digitalhouse.desafio03dh.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.desafio03dh.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_comic.view.*
import br.com.digitalhouse.desafio03dh.model.*

class AdapterComic(
    val comicList: ArrayList<Result>,
    val listener: OnClickListenerItem
) : RecyclerView.Adapter<AdapterComic.ComicItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicItem {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ComicItem(itemView)
    }

    override fun onBindViewHolder(holder: ComicItem, position: Int) {
        val comic = comicList[position]

        holder.comicChapter.text = "#${comic.id}"
        Glide.with(holder.comicImage.context)
            .load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
            .placeholder(R.drawable.ic_baseline_photo_camera_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .fallback(R.drawable.ic_baseline_image_not_supported_24)
            .into(holder.comicImage)
    }

    inner class ComicItem(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val comicImage: ImageView = itemView.itemIVComicImage
        val comicChapter: TextView = itemView.itemTVComicChapter

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.OnClickItem(position)
        }
    }

    override fun getItemCount() = comicList.size
}

interface OnClickListenerItem {
    fun OnClickItem(position: Int)
}