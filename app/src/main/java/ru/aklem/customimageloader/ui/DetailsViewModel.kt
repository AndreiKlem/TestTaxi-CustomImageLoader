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
import ru.aklem.customimageloader.util.Result
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val orderDetailsRepository: OrderDetailsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderValue = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = orderValue

    private val imageValue: MutableLiveData<Result<Bitmap>> = MutableLiveData(Result.InProgress)
    val image: LiveData<Result<Bitmap>>
        get() = imageValue

    init {
        savedStateHandle.get<Long>(ORDER_ID_KEY)?.let { orderId ->
            viewModelScope.launch(ioDispatcher) {
                val order = orderDetailsRepository.getOrderById(orderId)
                orderValue.postValue(order)
                loadImage(order.vehicle.photo)
            }
        }
    }

    private fun loadImage(urn: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = orderDetailsRepository.getImage(urn)
            imageValue.postValue(result)
        }
    }

    fun onTryAgainClick() {
        order.value?.vehicle?.photo?.let {
            loadImage(it)
        }
    }

}