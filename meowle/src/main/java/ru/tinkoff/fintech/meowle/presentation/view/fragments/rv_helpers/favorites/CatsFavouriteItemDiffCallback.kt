package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.favorites

import androidx.recyclerview.widget.DiffUtil
import ru.tinkoff.fintech.meowle.presentation.shared.favourites.FavouriteCat

class CatsFavouriteItemDiffCallback : DiffUtil.ItemCallback<FavouriteCat>() {
    override fun areItemsTheSame(oldItem: FavouriteCat, newItem: FavouriteCat): Boolean {
        return oldItem.cat.id == newItem.cat.id && oldItem.catPhoto == newItem.catPhoto
    }

    override fun areContentsTheSame(oldItem: FavouriteCat, newItem: FavouriteCat): Boolean {
        return oldItem == newItem
    }
}