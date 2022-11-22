package ru.aklem.customimageloader.util

import ru.aklem.customimageloader.data.remote.entity.PriceNetworkEntity
import java.text.NumberFormat
import java.util.*
import kotlin.math.pow

interface ConvertPrice {

    fun getTotal(): String

    class Base(private val price: PriceNetworkEntity) : ConvertPrice {
        override fun getTotal(): String {
            return try {
                if (price.amount < 0) throw IllegalArgumentException()
                val currency = Currency.getInstance(price.currency)
                val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
                numberFormat.currency = currency
                val price = price.amount / 10.0.pow(currency.defaultFractionDigits)
                numberFormat.format(price)
            } catch (e: Exception) {
                "Error parsing price"
            }
        }
    }

}