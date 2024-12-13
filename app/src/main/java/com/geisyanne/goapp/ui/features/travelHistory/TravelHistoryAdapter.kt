package com.geisyanne.goapp.ui.features.travelHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geisyanne.goapp.databinding.ItemRvTravelHistoryBinding
import com.geisyanne.goapp.domain.model.RideModel
import com.geisyanne.goapp.util.formatDate
import com.geisyanne.goapp.util.formatToBRL

class TravelHistoryAdapter
    : RecyclerView.Adapter<TravelHistoryAdapter.TravelHistoryHolder>() {

    private var rideHistory: List<RideModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHistoryHolder {
        val binding =
            ItemRvTravelHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelHistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelHistoryHolder, position: Int) {
        val rideOption = rideHistory[position]
        holder.bind(rideOption)
    }

    override fun getItemCount(): Int = rideHistory.size

    fun updateData(newHistory: List<RideModel>) {
        if (rideHistory != newHistory) {
            rideHistory = newHistory
            notifyDataSetChanged()
        }
    }

    inner class TravelHistoryHolder(private val binding: ItemRvTravelHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ride: RideModel) {
            binding.txtDateHistory.text = ride.date?.formatDate()
            binding.txtDriverNameHistory.text = ride.driver?.name
            binding.txtOriginHistory.text = ride.origin
            binding.txtDestinationHistory.text = ride.destination
            binding.txtDistanceHistory.text = ride.distance.toString()
            binding.txtDurationHistory.text = ride.duration.toString()
            binding.txtValueHistory.text = ride.value?.formatToBRL()
        }
    }
}