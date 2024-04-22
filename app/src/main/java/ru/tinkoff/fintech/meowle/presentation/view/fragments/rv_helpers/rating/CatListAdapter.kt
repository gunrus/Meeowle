package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.rating

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Vote

class CatListAdapter : ListAdapter<Cat, CatListViewHolder>(CatsItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_rating_item, parent, false)
        return CatListViewHolder(view)    }

    var votePosition = Vote.LIKES
    var onCatItemClickListener: ((Cat) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CatListViewHolder, position: Int) {
        val catDetails = getItem(position)
        holder.itemView.setOnClickListener { onCatItemClickListener?.invoke(catDetails) }

        holder.twName.text = catDetails.name

        holder.twRatingPosition.text = "${position + 1}."

        when (votePosition) {
            Vote.LIKES -> {
                holder.tw_likes.text = "\uD83D\uDC4D ${catDetails.likes}"
                holder.tw_dislikes.visibility = View.GONE
                holder.tw_likes.visibility = View.VISIBLE
            }
            Vote.DISLIKES -> {
                holder.tw_dislikes.text = "\uD83D\uDC4E ${catDetails.dislikes}"
                holder.tw_likes.visibility = View.GONE
                holder.tw_dislikes.visibility = View.VISIBLE
            }
        }
    }
}