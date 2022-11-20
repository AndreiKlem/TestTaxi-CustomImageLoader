package ru.aklem.customimageloader.domain

import ru.aklem.customimageloader.domain.model.Order


interface OrderDetailsRepository {

    suspend fun getOrderById(orderId: Long): Order

}