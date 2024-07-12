package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.favorites

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ru.tinkoff.fintech.meowle.R

class CatFavoritesListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val iwAvatar: ShapeableImageView = view.findViewById(R.id.cat_avatar)
    val twName: TextView = view.findViewById(R.id.cat_name)
}