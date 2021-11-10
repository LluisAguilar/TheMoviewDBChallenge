package com.lyft.android.themoviedbtest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.luis.android.themoviechallenge.R
import com.luis.android.themoviechallenge.view.adapter.MoviesRecyclerAdapter
import com.luis.android.themoviechallenge.view.model.PopularMoviesData
import kotlinx.android.synthetic.main.fragment_movies.view.*

class MoviesFragment : Fragment(), MoviesRecyclerAdapter.OnMovieItemClickListener {

    private lateinit var mView:View
    private lateinit var mMoviesRecyclerAdapter:MoviesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_movies, container, false)

        mMoviesRecyclerAdapter = MoviesRecyclerAdapter(arrayListOf(),this)
        mView.popular_movies_recycler.layoutManager = GridLayoutManager(context,3)
        mView.popular_movies_recycler.adapter = mMoviesRecyclerAdapter

        return mView
    }

    companion object {
        @JvmStatic
        fun getInstance() = MoviesFragment()
    }

    override fun OnMovieItemClick(movieId: String, title:String, position: Int) {
        Toast.makeText(context, title,Toast.LENGTH_SHORT).show()
    }

    fun setMoviesView(moviesList : ArrayList<PopularMoviesData>){
        try {
            mMoviesRecyclerAdapter.updateAdapter(moviesList)
        } catch (e:UninitializedPropertyAccessException){
            e.printStackTrace()
        }
    }
}