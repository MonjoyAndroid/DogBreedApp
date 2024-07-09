package com.monjoy.myimageapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.monjoy.myimageapp.R
import com.monjoy.myimageapp.interfaces.OnItemClickListener
import com.monjoy.myimageapp.room.entity.BreedDetail

class BreedAdapter(private val breedList: List<BreedDetail>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val breedName: TextView = itemView.findViewById(R.id.breedName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.breedName.text = breedList[position].breedName

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(breedList[position])
        }
    }

    override fun getItemCount(): Int {
        return breedList.size
    }
}