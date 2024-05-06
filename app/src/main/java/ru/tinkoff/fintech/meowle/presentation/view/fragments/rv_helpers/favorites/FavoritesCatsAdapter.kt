package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.shared.favourites.FavouriteCat

class FavoritesCatsAdapter : ListAdapter<FavouriteCat, CatFavoritesListViewHolder>(CatsFavouriteItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFavoritesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_favourite_item_redesign, parent, false)
        return CatFavoritesListViewHolder(view)
    }
    var onCatItemClicked: ((Cat) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CatFavoritesListViewHolder, position: Int) {

        val favouriteCat = getItem(position)
        val res = holder.itemView.context.resources

        holder.itemView.setOnClickListener {
            onCatItemClicked?.invoke(favouriteCat.cat)
        }

        holder.twName.text = favouriteCat.cat.name

        Glide.with(holder.itemView.context)
            .load(favouriteCat.catPhoto)
            .placeholder(R.drawable.sleepy_cat)
            .error(R.drawable.sleepy_cat)
            .sizeMultiplier(0.5f)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            .into(holder.iwAvatar)
    }
}
