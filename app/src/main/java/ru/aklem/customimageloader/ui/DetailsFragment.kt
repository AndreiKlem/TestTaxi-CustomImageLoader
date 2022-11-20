package ru.aklem.customimageloader.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.aklem.customimageloader.R
import ru.aklem.customimageloader.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        viewModel.loadOrderDetails()
        observeOrder()
    }

    private fun observeOrder() {
        viewModel.order.observe(viewLifecycleOwner) { order ->
            with(binding) {
                cityTv.text = if (order.startAddress.city != order.endAddress.city) {
                    listOf(order.startAddress.city, order.endAddress.city)
                        .joinToString( " - ")
                } else
                    order.startAddress.city
                startPlaceTv.text = order.startAddress.place
                endPlaceTv.text = order.endAddress.place
                dateDetailsTv.text = order.date
                priceTv.text = order.price
                timeTv.text = order.time
                vehicleTv.text = listOf(order.vehicle.modelName, order.vehicle.regNumber)
                    .joinToString(", ")
                driverTv.text = order.vehicle.driverName
            }
        }
    }

    companion object {
        const val ORDER_ID_KEY = "order_id_key"
    }
}