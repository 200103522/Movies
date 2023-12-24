package com.example.movies.Activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movies.Adapters.SliderAdapters
import com.example.movies.Domain.Movie
import com.example.movies.Domain.SliderItems
import com.example.movies.R
import kotlin.math.abs

class DashboardActivity : AppCompatActivity() {
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var up_moviesRecyclerView: RecyclerView
    private lateinit var bestMoviesAdapter: MoviesAdapter
    private lateinit var up_MoviesAdapter: MoviesAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var slideHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewPager2 = findViewById(R.id.viewpagerSlider)
        moviesRecyclerView = findViewById(R.id.view2)
        up_moviesRecyclerView = findViewById(R.id.view4)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        up_moviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bestMoviesAdapter = MoviesAdapter(this) { movie ->

            showMovieDetailDialog(movie)
        }
        up_MoviesAdapter = MoviesAdapter(this) { movie ->
            showMovieDetailDialog(movie)
        }
        val movies = listOf(
            Movie(R.drawable.shawshank, "The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."),
            Movie(R.drawable.godfather, "The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."),
            Movie(R.drawable.batman, "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."),
            Movie(R.drawable.sigma, "Fight club", "FOR THE REAL SIGMAS")
        )
        bestMoviesAdapter.submitList(movies)
        moviesRecyclerView.adapter = bestMoviesAdapter
        val upcoming_movies = listOf(
            Movie(R.drawable.barbie, "Barbie", "Film about a barbie girl"),
            Movie(R.drawable.killers, "Killers of the Flower Moon", "Fil with Leonardo Di Caprio :)."),
            Movie(R.drawable.dune, "Dune: Part two", "Sequel of the famous film Dune."),
        )
        up_MoviesAdapter.submitList(upcoming_movies)
        up_moviesRecyclerView.adapter = up_MoviesAdapter
        initView()
        banners()

    }

    private fun initView() {
        slideHandler = Handler(Looper.getMainLooper())
    }

    private fun showMovieDetailDialog(movie: Movie) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(movie.title)
        dialogBuilder.setMessage(movie.description)
        dialogBuilder.setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
    private fun banners() {
        val sliderItems = mutableListOf(
            SliderItems(R.drawable.wide),
            SliderItems(R.drawable.wide1),
            SliderItems(R.drawable.wide3)
        )

        viewPager2.adapter = SliderAdapters(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.setCurrentItem(1, false)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                slideHandler.removeCallbacks(sliderRunnable)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }
    class MoviesAdapter(
        private val context: Context,
        private val onClick: (Movie) -> Unit
    ) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

        private var movies: List<Movie> = emptyList()

        fun submitList(movieList: List<Movie>) {
            movies = movieList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
            return MovieViewHolder(view, onClick)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.bind(movies[position])
        }

        override fun getItemCount(): Int = movies.size

        inner class MovieViewHolder(itemView: View, val onClick: (Movie) -> Unit) : RecyclerView.ViewHolder(itemView) {
            private val imageView: ImageView = itemView.findViewById(R.id.movie_image)
            private val titleView: TextView = itemView.findViewById(R.id.movie_title)

            fun bind(movie: Movie) {
                imageView.setImageResource(movie.image)
                titleView.text = movie.title
                itemView.setOnClickListener { onClick(movie) }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacksAndMessages(null)
    }

    override fun onResume() {
        super.onResume()
        if (::slideHandler.isInitialized) {
            slideHandler.postDelayed(sliderRunnable, 2000)
        }
    }
}