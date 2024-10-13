package ru.sweetmilk.movieapp.cases.movieList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ru.sweetmilk.movieapp.MovieApp
import ru.sweetmilk.movieapp.databinding.FragmentMovieListBinding
import javax.inject.Inject


class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MovieListFragmentViewModel by viewModels<MovieListFragmentViewModel> {
        viewModelFactory
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: MovieListAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MovieApp).appComponent.addMovieListComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        _adapter = MovieListAdapter(
            requireContext(),
            lifecycleScope,
            viewModel::loadMovieImage
        )
        binding.movieList.adapter = _adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMovieList()
        viewModel.movieList.observe(viewLifecycleOwner) { response ->
            _adapter?.submitList(response?.items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}