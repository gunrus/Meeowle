package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.rating

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ru.tinkoff.fintech.meowle.R

class CatListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val iv_crown : ImageView = view.findViewById(R.id.cat_crown)
    val twName: TextView = view.findViewById(R.id.cat_name)

    val tw_count: TextView = view.findViewById(R.id.tw_count)
    val ib_likes: ImageButton = view.findViewById(R.id.ib_like)
}