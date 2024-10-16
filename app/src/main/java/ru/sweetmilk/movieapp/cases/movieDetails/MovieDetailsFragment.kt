package ru.sweetmilk.movieapp.cases.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sweetmilk.movieapp.databinding.FragmentMovieDetailsBinding
import java.util.UUID

class MovieDetailsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movieId = arguments?.getSerializable(ARG_MOVIE_ID) as UUID?
        binding.text.text = movieId.toString()
        return binding.root
    }

    companion object {
        val ARG_MOVIE_ID = "ARG_MOVIE_ID"
    }
}