package ru.aklem.customimageloader.ui

import android.graphics.Bitmap
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.aklem.customimageloader.di.IoDispatcher
import ru.aklem.customimageloader.domain.OrderDetailsRepository
import ru.aklem.customimageloader.domain.model.Order
import ru.aklem.customimageloader.ui.DetailsFragment.Companion.ORDER_ID_KEY
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val orderDetailsRepository: OrderDetailsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderValue = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = orderValue

    private val imageValue = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap>
        get() = imageValue

    fun loadOrderDetails() {
        val orderId = savedStateHandle.get<Long>(ORDER_ID_KEY) ?: throw IllegalArgumentException()
        viewModelScope.launch(ioDispatcher) {
            val order = orderDetailsRepository.getOrderById(orderId)
            orderValue.postValue(order)
            imageValue.postValue(orderDetailsRepository.getImage(order.vehicle.photo))
        }
    }

}