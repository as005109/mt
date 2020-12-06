package com.lisovskaya.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lisovskaya.lab3.api.OmdbSearchResultMovie
import com.lisovskaya.lab3.db.MovieDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieListAdapter (private var listOfItems: List<OmdbSearchResultMovie>,
                        private val dbDao: MovieDao?,
                        private val onItemClickListener: MyItemClickListener
) : RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder>() {

    fun setNewListOfItems(newList: List<OmdbSearchResultMovie>) {
        listOfItems = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MovieItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = listOfItems[position]
        holder.textView.text = movie.title
        Glide.with(holder.itemView.context)
            .load(movie.posterUrl)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            onItemClickListener.onMyItemClick(movie)
        }
        holder.button.setOnClickListener {
            onItemClickListener.addToFavorites(movie)
            this.notifyItemChanged(position)
        }
        findMovieById(movie.imdbId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isMovieFavorite, error ->
                if (isMovieFavorite) {
                    holder.button.setImageResource(R.drawable.ic_heart_red_24dp)
                }
            }
    }

    override fun onViewRecycled(holder: MovieItemViewHolder) {
        Glide.with(holder.itemView.context).clear(holder.imageView)
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }


    private fun findMovieById(id: String): Single<Boolean> {
        return Single.create<Boolean> {
            SingleOnSubscribe<Boolean> { single ->
                val movie = dbDao?.getMovieById(id)
                if (movie != null) {
                    single.onSuccess(true)
                } else {
                    single.onSuccess(false)
                }
            }
                .subscribe(it)
        }
    }

    inner class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.listItemImageView)
        val textView: TextView = itemView.findViewById(R.id.listItemTextView)
        val button: ImageButton = itemView.findViewById(R.id.favoritesImageButton)
    }

    interface MyItemClickListener {
        fun onMyItemClick(selectedMovie: OmdbSearchResultMovie)
        fun addToFavorites(selectedMovie: OmdbSearchResultMovie)
    }
}