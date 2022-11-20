package ru.aklem.customimageloader.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aklem.customimageloader.domain.model.Order

interface OrderListRepository {

    suspend fun getOrders(): Flow<PagingData<Order>>
    suspend fun refreshOrders()

}