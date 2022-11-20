package ru.aklem.customimageloader.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.aklem.customimageloader.data.local.OrdersDao
import ru.aklem.customimageloader.data.local.entity.OrderRoomEntity
import ru.aklem.customimageloader.data.remote.OrdersApi
import ru.aklem.customimageloader.data.remote.entity.toOrderRoomEntity
import ru.aklem.customimageloader.di.IoDispatcher
import ru.aklem.customimageloader.domain.OrderListRepository
import ru.aklem.customimageloader.domain.model.Order
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderListRepositoryImpl @Inject constructor(
    private val ordersDao: OrdersDao,
    private val ordersApi: OrdersApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : OrderListRepository {

    override suspend fun getOrders(): Flow<PagingData<Order>> {
        val loader: OrdersPageLoader = { pageIndex, pageSize ->
            getOrders(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { OrdersPagingSource(loader) }
        ).flow
    }

    override suspend fun refreshOrders() {
        val orders = ordersApi.getOrders()
        return ordersDao.save(orders.map { it.toOrderRoomEntity() })
    }

    private suspend fun getOrders(pageIndex: Int, pageSize: Int): List<Order> =
        withContext(ioDispatcher) {
            val offset = pageIndex * pageSize
            val list = ordersDao.getDatabaseOrders(pageSize, offset)
            return@withContext list.map(OrderRoomEntity::toOrder)
        }

    private companion object {
        const val PAGE_SIZE = 30
    }

}