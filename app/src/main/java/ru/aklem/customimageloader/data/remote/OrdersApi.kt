package ru.aklem.customimageloader.data.remote

import retrofit2.http.GET
import ru.aklem.customimageloader.data.remote.entity.OrderNetworkEntity

interface OrdersApi {

    @GET("orders.json")
    suspend fun getOrders(): List<OrderNetworkEntity>

}