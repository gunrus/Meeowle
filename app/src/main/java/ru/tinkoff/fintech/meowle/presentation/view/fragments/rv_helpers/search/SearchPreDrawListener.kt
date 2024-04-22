package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.SearchCatListItem.Companion.TYPE_CAT
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.SearchCatListItem.Companion.TYPE_DIVIDER

class SearchPreDrawListener(private val itemView: View, private val viewType: Int, private val parent: ViewGroup) : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            val lp = itemView.layoutParams
            if (lp is StaggeredGridLayoutManager.LayoutParams) {
                when (viewType) {
                    TYPE_DIVIDER -> lp.isFullSpan = true
                    TYPE_CAT -> {
                        lp.isFullSpan = false
                        lp.width = itemView.width / 2
                    }
                }
                itemView.layoutParams = lp
                val lm = (parent as RecyclerView).layoutManager as StaggeredGridLayoutManager
                lm.invalidateSpanAssignments()
            }
            itemView.viewTreeObserver.removeOnPreDrawListener(this)
            return true
        }
    }