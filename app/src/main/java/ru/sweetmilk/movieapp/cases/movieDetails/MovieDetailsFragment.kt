package ru.sweetmilk.movieapp.cases.movieDetails

import android.content.Context
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.Movie
import ru.sweetmilk.movieapp.databinding.FragmentMovieDetailsBinding
import ru.sweetmilk.movieapp.utils.DictionaryToStringConverter
import java.util.UUID
import javax.inject.Inject

class MovieDetailsFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieDetailsFragmentViewModel by viewModels<MovieDetailsFragmentViewModel> {
        viewModelFactory
    }

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieApp).appComponent
            .addMovieDetailsComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val movieId = arguments?.getSerializable(ARG_MOVIE_ID) as UUID?

        viewModel.loadMovieDetails(movieId).observe(viewLifecycleOwner) {
            updateUI(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.movieContent.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

    private fun updateUI(movie: Movie?) {
        binding.movieTitle.text = movie?.title
        binding.movieGenres.apply {
            val genres = DictionaryToStringConverter.convert(movie?.genres)
            isVisible = !genres.isNullOrEmpty()
            text = getString(R.string.movie_genres, genres)
        }

        binding.movieReleaseDate.apply {
            val dateString = DateFormat
                .getPatternInstance(DateFormat.YEAR_ABBR_MONTH_DAY)
                .format(movie?.releaseDate)

            isVisible = !dateString.isNullOrEmpty()
            text = getString(R.string.movie_release_date, dateString)
        }

        binding.movieDuration.apply {
            isVisible = (movie?.durationInMinutes ?: 0) > 0
            text = getString(R.string.movie_duration, movie?.durationInMinutes)
        }

        binding.movieDescription.apply {
            isVisible = !movie?.description.isNullOrEmpty()
            text = movie?.description
        }

        binding.movieDescriptionLabel.isVisible = !movie?.description.isNullOrEmpty()

        binding.moviePgInfo.apply {
            isVisible = !movie?.pgInfo.isNullOrEmpty()
            text = getString(R.string.movie_pg_info, movie?.pgInfo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val ARG_MOVIE_ID = "ARG_MOVIE_ID"
    }
}