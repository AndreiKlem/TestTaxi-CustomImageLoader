package ru.aklem.customimageloader.data.remote.entity

import ru.aklem.customimageloader.data.local.entity.OrderRoomEntity
import ru.aklem.customimageloader.util.ConvertPrice
import ru.aklem.customimageloader.util.DateUtil

data class OrderNetworkEntity(
    val id: Int,
    val startAddress: AddressNetworkEntity,
    val endAddress: AddressNetworkEntity,
    val price: PriceNetworkEntity,
    val orderTime: String,
    val vehicle: VehicleNetworkEntity
)

fun OrderNetworkEntity.toOrderRoomEntity(): OrderRoomEntity {
    return OrderRoomEntity(
        id = this.id.toLong(),
        startCity = this.startAddress.city,
        startAddress = this.startAddress.address,
        endCity = this.endAddress.city,
        endAddress = this.endAddress.address,
        price = ConvertPrice.Base(this.price).getTotal(),
        orderTime = DateUtil.Base().getMilliseconds(this.orderTime),
        regNumber = this.vehicle.regNumber,
        modelName = this.vehicle.modelName,
        photo = this.vehicle.photo,
        driverName = this.vehicle.driverName,
    )
}