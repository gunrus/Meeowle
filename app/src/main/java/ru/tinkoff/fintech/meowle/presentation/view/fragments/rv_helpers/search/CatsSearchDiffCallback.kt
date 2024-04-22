package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search

import androidx.recyclerview.widget.DiffUtil

class CatsSearchDiffCallback : DiffUtil.ItemCallback<SearchCatListItem>() {
    override fun areItemsTheSame(oldItem: SearchCatListItem, newItem: SearchCatListItem): Boolean {
        return when (oldItem is DataItemSearch && newItem is DataItemSearch) {
            true -> {
                oldItem.catItem.id == newItem.catItem.id
            }

            false -> {
                (oldItem as DividerItemSearch).letter == (newItem as DividerItemSearch).letter
            }
        }
    }

    override fun areContentsTheSame(oldItem: SearchCatListItem, newItem: SearchCatListItem): Boolean {
        return oldItem == newItem
    }
}