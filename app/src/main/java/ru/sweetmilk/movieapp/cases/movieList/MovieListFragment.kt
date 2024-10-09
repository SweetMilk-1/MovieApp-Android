package ru.sweetmilk.movieapp.cases.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.cases.movieList.viewModel.MovieListFragmentViewModel
import ru.sweetmilk.movieapp.databinding.FragmentMovieListBinding
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding


class MovieListFragment : Fragment() {
    private val viewModel: MovieListFragmentViewModel by viewModels()

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: MovieListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        _adapter = MovieListAdapter()
        binding.movieList.adapter = _adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieListLiveData().observe(viewLifecycleOwner) {response ->
            _adapter?.submitList(response.items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MovieListAdapter: ListAdapter<MovieListItem, MovieListItemViewHolder>(
        MovieListItemDiffUtil()
    ) {
        private val layoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListItemViewHolder {
            val binding = HolderMovieListItemBinding.inflate(layoutInflater, parent, false)
            return MovieListItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MovieListItemViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
            lifecycleScope.launch {
                val bitmap = viewModel.getMovieImage(item.id)
                holder.bindImage(bitmap)
            }
        }
    }
}