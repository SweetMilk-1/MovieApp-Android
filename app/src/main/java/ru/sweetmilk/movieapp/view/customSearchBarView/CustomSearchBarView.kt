package ru.sweetmilk.movieapp.view.customSearchBarView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.ViewCustomSearchBarBinding

class CustomSearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes, defStyleAttr) {

    private var searchString: String? = null

    private var onSearchListener: ((search: String?)->Unit)? = null
    fun setOnSearchListener(callback: (search: String?)->Unit) {
        onSearchListener = callback
    }

    private var isSearching: Boolean = false
        set(value) {
            binding.searchText.isVisible = value
            binding.cancelSearchButton.isVisible = value
            if (value) {
                binding.searchView.visibility = View.INVISIBLE
                binding.searchText.text = context.resources.getString(R.string.search_text, searchString)
            }
            else {
                binding.searchView.visibility = View.VISIBLE
                binding.searchView.setQuery(null, false)
            }
            field = value
        }

    private val binding = ViewCustomSearchBarBinding.inflate(LayoutInflater.from(context), this)
        .apply {
            searchView.apply {
                setIconifiedByDefault(false)
                setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        searchString = query
                        onSearchListener?.invoke(searchString)
                        isSearching = true
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
            cancelSearchButton.apply {
                setOnClickListener {
                    searchString = null
                    onSearchListener?.invoke(searchString)
                    isSearching = false
                }
            }
        }

    init {
        isSearching = false
    }
}

