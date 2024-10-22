package ru.sweetmilk.movieapp.cases.movieDetails

import android.content.Context
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.Movie
import ru.sweetmilk.movieapp.databinding.FragmentMovieDetailsBinding
import java.util.UUID
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

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
            if (it == null)
                return@observe
            updateUI(it)

        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.movieContent.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

    private fun updateUI(movie: Movie) {
        lifecycleScope.launch {
            val drawable = viewModel.loadMovieImage(movie.id)
            if (drawable != null) {
                binding.movieImage.setImageDrawable(drawable)
            } else {
                val noPhotoDrawable =
                    ResourcesCompat.getDrawable(resources, R.drawable.no_photo, null)
                binding.movieImage.setImageDrawable(noPhotoDrawable)
            }
        }


        binding.movieTitle.text = movie.title
        binding.movieGenres.apply {

            val genres = movie.genres.joinToString(transform = { it.name }).lowercase()
            isVisible = genres.isNotEmpty()
            text = getString(R.string.movie_genres, genres)
        }

        binding.movieReleaseDate.apply {
            val dateString = DateFormat
                .getPatternInstance(DateFormat.YEAR_ABBR_MONTH_DAY)
                .format(movie.releaseDate)

            isVisible = !dateString.isNullOrEmpty()
            text = getString(R.string.movie_release_date, dateString)
        }

        binding.movieDuration.apply {
            isVisible = movie.durationInMinutes > 0
            text = getString(R.string.movie_duration, movie.durationInMinutes)
        }

        binding.movieDescriptionLabel.isVisible = movie.description.isNotEmpty()

        binding.movieDescription.apply {
            isVisible = movie.description.isNotEmpty()
            text = movie.description
        }

        binding.movieActorsLabel.isVisible = movie.actors.isNotEmpty()

        binding.movieActors.apply {
            isVisible = movie.actors.isNotEmpty()
            adapter = ActorsInMovieDetailsAdapter(
                layoutInflater,
                movie.actors,
                lifecycleScope,
                viewModel::loadActorPhoto
            )
        }

        binding.moviePgInfo.apply {
            isVisible = movie.pgInfo.isNotEmpty()
            text = getString(R.string.movie_pg_info, movie.pgInfo)
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
