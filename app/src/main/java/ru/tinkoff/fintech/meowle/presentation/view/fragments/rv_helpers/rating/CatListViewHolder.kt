package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.rating

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tinkoff.fintech.meowle.R

class CatListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val twRatingPosition: TextView = view.findViewById(R.id.cat_rating_position)
    val twName: TextView = view.findViewById(R.id.cat_name)
    val tw_likes: TextView = view.findViewById(R.id.cat_likes)
    val tw_dislikes: TextView = view.findViewById(R.id.cat_dislikes)
}