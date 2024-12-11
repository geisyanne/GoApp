package com.geisyanne.goapp.ui.features.travelOptions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geisyanne.goapp.databinding.ItemRvTravelOptionsBinding
import com.geisyanne.goapp.domain.model.RideOptionModel

class TravelOptionsAdapter(
    private var rideOptions: List<RideOptionModel> = emptyList(),
    private val onItemClick: (RideOptionModel) -> Unit
    ) : RecyclerView.Adapter<TravelOptionsAdapter.TravelOptionsHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelOptionsHolder {
            val binding = ItemRvTravelOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TravelOptionsHolder(binding)
        }

        override fun onBindViewHolder(holder: TravelOptionsHolder, position: Int) {
            val rideOption = rideOptions[position]
            holder.bind(rideOption)
        }

        override fun getItemCount(): Int = rideOptions.size

    fun updateData(newOptions: List<RideOptionModel>) {
        if (rideOptions != newOptions) {
            rideOptions = newOptions
            notifyDataSetChanged()
        }
    }

        inner class TravelOptionsHolder(private val binding: ItemRvTravelOptionsBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(rideOption: RideOptionModel) {
                binding.txtDriverItemOptions.text = rideOption.name
                binding.txtDescItemOptions.text = rideOption.description
                binding.txtVehicleItemOptions.text = rideOption.vehicle
                binding.txtRatingItemOptions.text = rideOption.review?.rating.toString()
                binding.txtValueItemOptions.text = rideOption.value.toString()

                binding.btnChooseItemOptions.setOnClickListener {
                    onItemClick(rideOption)
                }
            }
        }
}