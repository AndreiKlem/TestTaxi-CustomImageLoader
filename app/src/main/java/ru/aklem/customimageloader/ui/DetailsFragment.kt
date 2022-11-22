package ru.aklem.customimageloader.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.aklem.customimageloader.R
import ru.aklem.customimageloader.databinding.FragmentDetailsBinding
import ru.aklem.customimageloader.util.Result

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        renderOrder()
        renderImage()
        setListeners()
    }

    private fun renderOrder() {
        viewModel.order.observe(viewLifecycleOwner) { order ->
            with(binding) {
                cityTv.text = if (order.startAddress.city != order.endAddress.city) {
                    listOf(order.startAddress.city, order.endAddress.city)
                        .joinToString(" - ")
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

    private fun renderImage() {
        viewModel.image.observe(viewLifecycleOwner) { imageResult ->
            binding.vehicleIv.visibility = GONE
            binding.errorGroup.visibility = GONE
            binding.imageProgressBar.visibility = GONE
            when (imageResult) {
                Result.InProgress -> {
                    binding.imageProgressBar.visibility = VISIBLE
                }
                is Result.Error -> {
                    binding.errorGroup.visibility = VISIBLE
                }
                is Result.Success -> {
                    binding.vehicleIv.setImageBitmap(imageResult.data)
                    binding.vehicleIv.visibility = VISIBLE
                }
            }
        }
    }

    private fun setListeners() {
        binding.retryButton.setOnClickListener {
            viewModel.onTryAgainClick()
        }
    }

    companion object {
        const val ORDER_ID_KEY = "order_id_key"
    }

}