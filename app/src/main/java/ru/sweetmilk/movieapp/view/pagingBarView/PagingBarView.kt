package ru.sweetmilk.movieapp.view.pagingBarView

import android.content.Context
import android.icu.text.CaseMap.Upper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.databinding.ViewPageBarBinding

class PagingBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes, defStyleAttr) {


    val binding = ViewPageBarBinding.inflate(LayoutInflater.from(context), this)
        .apply {
            previousPageButton.setOnClickListener {
                if (currentPage > 1) {
                    currentPage--
                    onPageChangeListener?.onChangePage(currentPage)
                    updateUI()
                }
            }
            nextPageButton.setOnClickListener {
                if (currentPage < pageCount) {
                    currentPage++
                    onPageChangeListener?.onChangePage(currentPage)
                    updateUI()
                }
            }
        }

    var onPageChangeListener: OnChangePageListener? = null

    private var currentPage: Int = 1
    private var pageCount: Int = 1

    init {
        clear()
    }

    fun setPagingParameters(currentPage: Int, pageCount: Int) {
        this.currentPage = currentPage
        this.pageCount = pageCount
        updateUI()
    }

    fun clear() {
        binding.pagingText.text = null
        binding.previousPageButton.visibility = View.INVISIBLE
        binding.nextPageButton.visibility = View.INVISIBLE
    }

    private fun updateUI() {
        binding.previousPageButton.visibility =
            if (currentPage > 1) View.VISIBLE else View.INVISIBLE
        binding.nextPageButton.visibility =
            if (currentPage < pageCount) View.VISIBLE else View.INVISIBLE

        binding.pagingText.text =
            context.resources.getString(R.string.paging_text, currentPage, pageCount)
    }


    interface OnChangePageListener {
        fun onChangePage(page: Int)
    }
}