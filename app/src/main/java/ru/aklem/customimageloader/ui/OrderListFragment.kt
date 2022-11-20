package ru.aklem.customimageloader.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.aklem.customimageloader.R
import ru.aklem.customimageloader.databinding.FragmentOrderListBinding
import ru.aklem.customimageloader.domain.model.Order
import ru.aklem.customimageloader.ui.base.OrderItemActionListener
import ru.aklem.customimageloader.ui.base.OrdersAdapter

@AndroidEntryPoint
class OrderListFragment : Fragment(R.layout.fragment_order_list) {

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var adapter: OrdersAdapter
    private val viewModel by viewModels<OrderListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderListBinding.bind(view)
        adapter = OrdersAdapter(object : OrderItemActionListener {
            override fun onOrderClick(orderId: Long) {
                openDetailsFragment(orderId)
            }
        })

        binding.ordersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.ordersRv.adapter = adapter
        observeOrders(adapter)
    }

    private fun openDetailsFragment(orderId: Long) {
        findNavController().navigate(
            R.id.action_orderListFragment_to_detailsFragment,
            bundleOf(DetailsFragment.ORDER_ID_KEY to orderId)
        )
    }

    private fun observeOrders(adapter: OrdersAdapter) {
        lifecycleScope.launch {
            viewModel.ordersFlow.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}