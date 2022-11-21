package ru.aklem.customimageloader.data.remote

import android.graphics.drawable.Drawable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.aklem.customimageloader.data.remote.entity.OrderNetworkEntity

interface OrdersApi {

    @GET("orders.json")
    suspend fun getOrders(): List<OrderNetworkEntity>

    @GET("images/{imageName}")
    suspend fun getVehicleImage(@Path("imageName") imageName: String): Drawable
}