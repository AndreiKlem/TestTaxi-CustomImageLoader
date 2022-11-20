package ru.aklem.customimageloader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.aklem.customimageloader.data.local.entity.OrderRoomEntity

@Database(
    version = 1,
    entities = [OrderRoomEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getOrdersDao(): OrdersDao

}