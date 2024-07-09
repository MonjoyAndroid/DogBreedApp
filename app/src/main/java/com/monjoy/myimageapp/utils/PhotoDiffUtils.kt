package com.monjoy.myimageapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.monjoy.myimageapp.room.entity.BreedDetail

class PhotoDiffUtils : DiffUtil.ItemCallback<BreedDetail>()  {
    override fun areItemsTheSame(oldItem: BreedDetail, newItem: BreedDetail): Boolean {
        return oldItem.id == newItem.id // Compare based on unique identifier
    }

    override fun areContentsTheSame(oldItem: BreedDetail, newItem: BreedDetail): Boolean {
        return oldItem == newItem // Compare content for equality
    }
}