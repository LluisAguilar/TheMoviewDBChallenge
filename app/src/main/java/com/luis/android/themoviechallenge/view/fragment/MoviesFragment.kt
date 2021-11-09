package com.lyft.android.themoviedbtest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luis.android.themoviechallenge.R

class MoviesFragment : Fragment() {

    private lateinit var v:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_movies, container, false)

        return v
    }

    companion object {
        @JvmStatic
        fun getInstance() = MoviesFragment()
    }
}