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
import ru.sweetmilk.movieapp.utils.KeyboardUtil
import ru.sweetmilk.movieapp.utils.SnackBarUtil
import javax.inject.Inject


class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieListFragmentViewModel by viewModels<MovieListFragmentViewModel> {
        viewModelFactory
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MovieApp).appComponent
            .addMovieListComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        adapter = MovieListAdapter(
            requireContext(),
            lifecycleScope,
            viewModel::fetchMovieImage,
            viewModel::fetchNextPage
        )

        binding.movieList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.setOnSearchListener {
            KeyboardUtil.hideKeyboard(requireActivity())
            viewModel.apply {
                search = it
                fetchFirstPage()
            }
        }

        viewModel.movieListLiveData.observe(viewLifecycleOwner) { list ->
            adapter.submitItems(list, viewModel.isLastPage)
        }

        viewModel.failedMessageLiveEvent.observe(viewLifecycleOwner) {
            adapter.onLoadingFailed()
            SnackBarUtil.showSnackBar(requireView(), it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}