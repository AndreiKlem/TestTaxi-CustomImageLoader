package ru.aklem.customimageloader.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.aklem.customimageloader.domain.model.Address
import ru.aklem.customimageloader.domain.model.Order
import ru.aklem.customimageloader.domain.model.Vehicle
import ru.aklem.customimageloader.util.DateUtil

@Entity(tableName = "orders")
data class OrderRoomEntity(
    @PrimaryKey val id: Long,
    val startCity: String,
    val startAddress: String,
    val endCity: String,
    val endAddress: String,
    val price: String,
    val orderTime: String,
    val regNumber: String,
    val modelName: String,
    val photo: String,
    val driverName: String
) {

    fun toOrder(): Order = Order(
        id = id,
        startAddress = Address(
            city = startCity,
            place = startAddress
        ),
        endAddress = Address(
            city = endCity,
            place = endAddress
        ),
        date = DateUtil.Base().getDate(orderTime),
        time = DateUtil.Base().getTime(orderTime),
        price = price,
        vehicle = Vehicle(
            regNumber = regNumber,
            modelName = modelName,
            photo = photo,
            driverName = driverName
        )
    )
}