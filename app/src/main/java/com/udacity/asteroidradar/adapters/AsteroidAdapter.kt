package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidRecyclerItemBinding

class AsteroidAdapter(private val click: (Asteroid) -> Unit) :
    RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {
    private var asteroidList = listOf<Asteroid>()

    class AsteroidViewHolder(binding: AsteroidRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val view = binding

        fun bind(item: Asteroid) {
            view.asteroid = item
            view.executePendingBindings()
            view.dateTv.text = item.closeApproachDate
            view.nameTv.text = item.codename
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AsteroidRecyclerItemBinding.inflate(layoutInflater, parent, false)

        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = asteroidList[position]

        holder.itemView.setOnClickListener {
            click(item)
        }
        return holder.bind(item)

    }

    override fun getItemCount(): Int = asteroidList.size


    fun updateList(asteroids: List<Asteroid>) {
       // asteroidList = asteroids
        notifyChanges(asteroidList, asteroids)

    }

    private fun notifyChanges(oldList: List<Asteroid>, newList: List<Asteroid>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        asteroidList = newList
        diff.dispatchUpdatesTo(this)
    }

}
