package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.SearchCatListItem.Companion.TYPE_CAT
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.SearchCatListItem.Companion.TYPE_DIVIDER


class CatsSearchListAdapter (private val listItems: List<SearchCatListItem>) :
    ListAdapter<SearchCatListItem, RecyclerView.ViewHolder>(CatsSearchDiffCallback()) {

    var onCatItemClickListener: ((Cat) -> Unit)? = null


    override fun getItemViewType(position: Int): Int {
        return listItems[position].type
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItem(position: Int): SearchCatListItem {
        return listItems[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dividerItemView = LayoutInflater.from(parent.context).inflate(R.layout.divider_item, parent, false)
        val dataItemView = LayoutInflater.from(parent.context).inflate(R.layout.cat_item_short, parent, false)

        return when (viewType) {
            TYPE_CAT -> {
                dataItemView.viewTreeObserver.addOnPreDrawListener(SearchPreDrawListener(dataItemView, TYPE_CAT,parent))
                CatListSearchViewHolder(dataItemView)
            }
            else -> {
                dividerItemView.viewTreeObserver.addOnPreDrawListener(SearchPreDrawListener(dataItemView, TYPE_DIVIDER, parent))
                DividerViewHolder(dividerItemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val res = holder.itemView.context.resources
        val item = getItem(position)

        when (holder.itemViewType) {
            TYPE_CAT -> {
                with (holder as CatListSearchViewHolder) {
                    with (item as DataItemSearch) {
                        holder.itemView.setOnClickListener {
                            onCatItemClickListener?.invoke(item.catItem)
                        }

                        holder.twName.text = item.catItem.name

                        when (item.catItem.gender) {
                            Gender.MALE -> holder.lb_gender.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_male,null ))
                            Gender.FEMALE -> holder.lb_gender.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_female,null ))
                            Gender.UNISEX -> holder.lb_gender.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.ic_unknown_gender,null ))
                        }
                    }
                }

            }
            TYPE_DIVIDER -> (holder as DividerViewHolder).tw_letter.text = (item as DividerItemSearch).letter
        }
    }

    inner class DividerViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tw_letter: TextView = view.findViewById(R.id.tw_letter)
    }

}