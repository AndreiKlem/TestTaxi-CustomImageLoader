package ru.aklem.customimageloader.data

import android.graphics.Bitmap
import ru.aklem.customimageloader.data.local.OrdersDao
import ru.aklem.customimageloader.domain.OrderDetailsRepository
import ru.aklem.customimageloader.domain.model.Order
import ru.aklem.customimageloader.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderDetailsRepositoryImpl @Inject constructor(
    private val ordersDao: OrdersDao,
    private val imageLoader: CustomImageLoader
): OrderDetailsRepository {

    override suspend fun getOrderById(orderId: Long): Order {
        return ordersDao.getById(orderId).toOrder()
    }

    override suspend fun getImage(urn: String): Result<Bitmap> {
        return imageLoader.getImage(urn)
    }

}