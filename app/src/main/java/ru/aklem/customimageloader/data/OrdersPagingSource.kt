package ru.aklem.customimageloader.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.aklem.customimageloader.domain.model.Order

typealias OrdersPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Order>

class OrdersPagingSource (private val loader: OrdersPageLoader): PagingSource<Int, Order>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {
        val pageIndex = params.key ?: 0
        return try {
            val orders = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = orders,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (orders.size == params.loadSize) pageIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Order>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }
}