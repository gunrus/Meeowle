package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ru.tinkoff.fintech.meowle.R

class CatListSearchViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val twName: TextView = view.findViewById(R.id.cat_name)
    val twDescription: TextView = view.findViewById(R.id.cat_description)
    val twLikes: TextView = view.findViewById(R.id.tw_likes)
    val twDislikes: TextView = view.findViewById(R.id.tw_dislikes)
    val lb_gender: ImageView = view.findViewById(R.id.cat_gender)
}