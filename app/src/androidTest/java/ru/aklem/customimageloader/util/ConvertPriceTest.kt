package ru.aklem.customimageloader.util

import org.junit.Assert
import org.junit.Test
import ru.aklem.customimageloader.data.remote.entity.PriceNetworkEntity

class ConvertPriceTest {

    @Test
    fun getTotalOnValidInputReturnPrice() {
        val price = PriceNetworkEntity(
            amount = 13500,
            currency = "RUB"
        )
        val converter = ConvertPrice.Base(price)
        val expected = "135,00 ₽"
        val actual = converter.getTotal()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getTotalOnInvalidInputReturnsErrorString() {
        val prices = listOf(
            PriceNetworkEntity(
                amount = 13500,
                currency = ""
            ),
            PriceNetworkEntity(
                amount = -1,
                currency = "RUB"
            ),
            PriceNetworkEntity(
                amount = 0,
                currency = ""
            ),
        )
        val expected = "Error parsing price"
        prices.forEach { price ->
            val converter = ConvertPrice.Base(price)
            val actual = converter.getTotal()

            Assert.assertEquals(expected, actual)
        }
    }
}