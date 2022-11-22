package ru.aklem.customimageloader.domain

import android.graphics.Bitmap
import ru.aklem.customimageloader.domain.model.Order
import ru.aklem.customimageloader.util.Result


interface OrderDetailsRepository {
    suspend fun getOrderById(orderId: Long): Order
    suspend fun getImage(urn: String): Result<Bitmap>
}