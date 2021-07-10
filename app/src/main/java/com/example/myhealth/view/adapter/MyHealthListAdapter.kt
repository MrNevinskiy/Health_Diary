package com.example.myhealth.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealth.databinding.ItemListBinding
import com.example.myhealth.model.data.HealthData

class MyHealthListAdapter(private val values: List<HealthData>) : RecyclerView.Adapter<MyHealthListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListBinding.inflate
            (LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idDate.text = item.date

        holder.idPressureDay.text = item.pressureDay
        holder.idPulseDay.text = item.pulseDay
        holder.itemTimeDay.text = item.timeDay

        holder.idTimeNight.text = item.timeNight
        holder.idPressureNight.text = item.pressureNight
        holder.idPulseNight.text = item.pulseNight
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        val idDate: TextView = binding.itemDate

        val idPressureDay: TextView = binding.itemPressureDay
        val idPulseDay: TextView = binding.itemPulseDay
        val itemTimeDay: TextView = binding.itemTimeDay

        val idTimeNight: TextView = binding.itemTimeNight
        val idPressureNight: TextView = binding.itemPressureNight
        val idPulseNight: TextView = binding.itemPulseNight
    }

}