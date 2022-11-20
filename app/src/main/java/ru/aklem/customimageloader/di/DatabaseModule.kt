package ru.aklem.customimageloader.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aklem.customimageloader.data.local.AppDatabase
import ru.aklem.customimageloader.data.local.OrdersDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideOrdersDao(database: AppDatabase): OrdersDao {
        return database.getOrdersDao()
    }

    private companion object {
        const val DATABASE_NAME = "orders.db"
    }

}