package ru.aklem.customimageloader.data

import ru.aklem.customimageloader.data.local.OrdersDao
import ru.aklem.customimageloader.domain.OrderDetailsRepository
import ru.aklem.customimageloader.domain.model.Order
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderDetailsRepositoryImpl @Inject constructor(
    private val ordersDao: OrdersDao
): OrderDetailsRepository {

    override suspend fun getOrderById(orderId: Long): Order {
        return ordersDao.getById(orderId).toOrder()
    }

}