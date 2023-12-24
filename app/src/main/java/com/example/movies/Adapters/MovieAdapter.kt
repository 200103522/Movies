package com.example.movies.Adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R

class MovieAdapter(
    private val movies: List<Movie>,
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View, val onClick: (Movie) -> Unit) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.movie_image)
        private val titleView: TextView = view.findViewById(R.id.movie_title)
        private var currentMovie: Movie? = null

        init {
            view.setOnClickListener {
                currentMovie?.let {
                    onClick(it)
                }
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie
            // Load image using Glide or another library
            titleView.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size
}

data class Movie(val image: Drawable, val title: String, val description: String)
