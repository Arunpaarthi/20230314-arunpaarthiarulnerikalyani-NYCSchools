package com.arun.nycschools.view.schoollist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arun.nycschools.databinding.ListItemLayoutBinding
import com.arun.nycschools.model.data.School

class NYCSchoolListAdapter(private val onItemClicked: (School) -> Unit): ListAdapter<School, NYCSchoolListAdapter.WeatherViewHolder>(
    DiffUtil
) {

    inner class WeatherViewHolder(private var binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: School, index: Int) {
            with(binding) {
                binding.tvSchoolName.text = data.school_name
                listItem.setOnClickListener { onItemClicked(data) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<School>() {
            override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
                return oldItem.dbn == newItem.dbn
            }

            override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
                return oldItem == newItem
            }

        }
    }


}