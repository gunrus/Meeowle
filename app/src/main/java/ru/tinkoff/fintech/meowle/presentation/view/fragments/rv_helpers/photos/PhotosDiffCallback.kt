package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.photos

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

class PhotosDiffCallback : DiffUtil.ItemCallback<Uri?>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }
}
