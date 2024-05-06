package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender


class CatsSearchListAdapter (private val listItems: List<Cat>) :
    ListAdapter<Cat,CatListSearchViewHolder>(CatsSearchDiffCallback()) {

    var onCatItemClickListener: ((Cat) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatListSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item_search_redisign, parent, false)
        return CatListSearchViewHolder(view)    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: CatListSearchViewHolder, position: Int) {
        val res = holder.itemView.context.resources
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            onCatItemClickListener?.invoke(item)
        }

        holder.twName.text = item.name + ","
        holder.twDescription.text = item.description
        holder.twLikes.text = item.likes.toString()
        holder.twDislikes.text = item.dislikes.toString()


        when (item.gender) {
            Gender.MALE -> holder.lb_gender.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_male,null ))
            Gender.FEMALE -> holder.lb_gender.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_female,null ))
            Gender.UNISEX -> holder.lb_gender.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_unknown_gender,null ))
        }

    }
    override fun getItem(position: Int): Cat {
        return listItems[position]
    }

}