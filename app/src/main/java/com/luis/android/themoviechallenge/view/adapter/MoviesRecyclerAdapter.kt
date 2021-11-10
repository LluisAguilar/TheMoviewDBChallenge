package com.luis.android.themoviechallenge.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luis.android.themoviechallenge.R
import com.luis.android.themoviechallenge.view.helper.StringUtils.Companion.MOVIES_POSTER_IMAGE_URL_PATH
import com.luis.android.themoviechallenge.view.model.PopularMoviesData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import java.lang.Exception
import java.util.*

class MoviesRecyclerAdapter(private var mmoviesList: ArrayList<PopularMoviesData>, listener: OnMovieItemClickListener):
    RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder>() {

    private var itemClickListener: OnMovieItemClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.bind(mmoviesList, itemClickListener)
    }

    override fun getItemCount(): Int {
        return mmoviesList.size
    }

    fun updateAdapter(mMoviesList: ArrayList<PopularMoviesData>){
        this.mmoviesList = mMoviesList
        notifyDataSetChanged()
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(mmoviesList: ArrayList<PopularMoviesData>, listener: OnMovieItemClickListener) {
            Picasso.get().load(MOVIES_POSTER_IMAGE_URL_PATH + mmoviesList.get(adapterPosition).poster_path).into(itemView.item_movie_iv)
            itemView.item_movie_title_tv.text = mmoviesList.get(adapterPosition).original_title

            itemView.item_movie_cv.setOnClickListener {
                listener.OnMovieItemClick(mmoviesList.get(adapterPosition).id.toString(),mmoviesList.get(adapterPosition).original_title,adapterPosition)
            }
        }
    }

    interface OnMovieItemClickListener {
        fun OnMovieItemClick(movieId:String, title:String, position: Int)
    }
}