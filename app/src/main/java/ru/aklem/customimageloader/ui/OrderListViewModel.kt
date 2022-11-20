package ru.aklem.customimageloader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import ru.aklem.customimageloader.di.IoDispatcher
import ru.aklem.customimageloader.domain.OrderListRepository
import ru.aklem.customimageloader.domain.model.Order
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val orderListRepository: OrderListRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val ordersFlowValue = MutableStateFlow<PagingData<Order>>(PagingData.empty())
    val ordersFlow: Flow<PagingData<Order>>
        get() = ordersFlowValue

    init {
        viewModelScope.launch(ioDispatcher) {
            orderListRepository.refreshOrders()
            val orders = orderListRepository.getOrders().cachedIn(viewModelScope)
            ordersFlowValue.emitAll(orders)
        }
    }
}