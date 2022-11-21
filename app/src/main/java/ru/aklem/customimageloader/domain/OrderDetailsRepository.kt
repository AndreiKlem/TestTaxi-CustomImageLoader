package ru.aklem.customimageloader.domain

import android.graphics.Bitmap
import ru.aklem.customimageloader.domain.model.Order


interface OrderDetailsRepository {
    suspend fun getOrderById(orderId: Long): Order
    suspend fun getImage(urn: String): Bitmap
}