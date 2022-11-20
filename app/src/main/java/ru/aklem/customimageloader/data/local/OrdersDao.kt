package ru.aklem.customimageloader.data.local

import androidx.room.*
import ru.aklem.customimageloader.data.local.entity.OrderRoomEntity

@Dao
interface OrdersDao {

    @Query("SELECT * FROM orders ORDER BY CAST (orderTime AS LONG) DESC LIMIT :limit OFFSET :offset")
    fun getDatabaseOrders(limit: Int, offset: Int): List<OrderRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(orders: List<OrderRoomEntity>)

    @Query("DELETE FROM orders")
    suspend fun clear()

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getById(id: Long): OrderRoomEntity

    @Transaction
    suspend fun refresh(orders: List<OrderRoomEntity>) {
        clear()
        save(orders)
    }

}