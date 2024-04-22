package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.photos

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.tinkoff.fintech.meowle.R

class PhotoListAdapter : ListAdapter<Uri?, PhotoListAdapter.ViewHolder>(PhotosDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.photo_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageLink = getItem(position)

        Glide.with(holder.itemView.context)
            .load(imageLink)
            .sizeMultiplier(0.5f)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            .into(holder.imageView)
    }

}

