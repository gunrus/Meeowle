package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.rating

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Vote

class CatListAdapter : ListAdapter<Cat, CatListViewHolder>(CatsItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item_rating_redesign, parent, false)
        return CatListViewHolder(view)    }

    var votePosition = Vote.LIKES
    var onCatItemClickListener: ((Cat) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CatListViewHolder, position: Int) {
        val catDetails = getItem(position)
        holder.itemView.setOnClickListener {
            onCatItemClickListener?.invoke(catDetails)
        }

        holder.twName.text = catDetails.name
        val res = holder.itemView.context.resources


        when (votePosition) {
            Vote.LIKES -> {
                holder.tw_count.text = catDetails.likes.toString()
                holder.ib_likes.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_like, null))
                when (position) {
                    0 -> {
                        with(holder.iv_crown) {
                            visibility = View.VISIBLE
                            setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.crown_gold,null ))
                        }
                    }
                    1 -> {
                        with(holder.iv_crown) {
                            visibility = View.VISIBLE
                            setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.crown_silver, null))
                        }
                    }
                    2 -> {
                        with(holder.iv_crown) {
                            visibility = View.VISIBLE
                            setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.crown_bronze, null))
                        }
                    }
                    else -> holder.iv_crown.visibility = View.GONE

                }
            }
            Vote.DISLIKES -> {
                holder.tw_count.text = catDetails.dislikes.toString()
                holder.ib_likes.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_dislike, null))
                holder.iv_crown.visibility = View.GONE
            }
        }
    }
}