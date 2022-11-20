package ru.aklem.customimageloader.domain.model

data class Order(
    val id: Long,
    val startAddress: Address,
    val endAddress: Address,
    val date: String,
    val time: String,
    val price: String,
    val vehicle: Vehicle
)
