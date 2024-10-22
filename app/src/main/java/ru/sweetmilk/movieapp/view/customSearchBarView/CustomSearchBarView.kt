package ru.sweetmilk.movieapp.view.customSearchBarView

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.ViewCustomSearchBarBinding
import androidx.core.view.isVisible as isVisible1

class CustomSearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes, defStyleAttr) {

    private var searchString: String? = null

    private var onSearchListener: ((search: String?) -> Unit)? = null
    fun setOnSearchListener(callback: (search: String?) -> Unit) {
        onSearchListener = callback
    }

    private var isSearching: Boolean = false

    private fun updateUI() {
        binding.searchText.isVisible1 = isSearching
        binding.cancelSearchButton.isVisible1 = isSearching
        if (isSearching) {
            binding.searchView.visibility = View.INVISIBLE
            binding.searchText.text =
                context.resources.getString(R.string.search_text, searchString)
        } else {
            binding.searchView.visibility = View.VISIBLE
            binding.searchView.setQuery(null, false)
        }
    }

    private val binding = ViewCustomSearchBarBinding.inflate(LayoutInflater.from(context), this)
        .apply {
            searchView.apply {
                setIconifiedByDefault(false)
                setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        searchString = query
                        isSearching = true
                        updateUI()

                        onSearchListener?.invoke(searchString)

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
                    isSearching = false
                    updateUI()

                    onSearchListener?.invoke(searchString)
                }
            }
        }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())

            putBoolean(IS_SEARCHING_KEY, isSearching)
            putString(SEARCH_STRING_KEY, searchString)
        }
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            searchString = newState.getString(SEARCH_STRING_KEY)
            isSearching = newState.getBoolean(IS_SEARCHING_KEY)
            newState = newState.getParcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)

        updateUI()

    }

    companion object {
        private const val IS_SEARCHING_KEY = "IS_SEARCHING_KEY"
        private const val SEARCH_STRING_KEY = "SEARCH_STRING_KEY"

        private const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
    }

    init {
        updateUI()
    }
}

