package ru.aklem.customimageloader.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aklem.customimageloader.data.OrderDetailsRepositoryImpl
import ru.aklem.customimageloader.data.OrderListRepositoryImpl
import ru.aklem.customimageloader.domain.OrderDetailsRepository
import ru.aklem.customimageloader.domain.OrderListRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindOrdersRepository(ordersRepositoryImpl: OrderListRepositoryImpl): OrderListRepository

    @Binds
    fun bindOrderDetailsRepository(
        orderDetailsRepositoryImpl: OrderDetailsRepositoryImpl
    ): OrderDetailsRepository

}