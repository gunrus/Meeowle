package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.favorites

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ru.tinkoff.fintech.meowle.R

class CatFavoritesListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val iwAvatar: ShapeableImageView = view.findViewById(R.id.cat_avatar)
    val twName: TextView = view.findViewById(R.id.cat_name)
    val tw_likes: TextView = view.findViewById(R.id.cat_likes)
    val tw_dislikes: TextView = view.findViewById(R.id.cat_dislikes)
    val lb_gender: ImageView = view.findViewById(R.id.cat_gender)
    val lb_favorite : ImageButton = view.findViewById(R.id.lb_favourite)
}