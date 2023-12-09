package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.adapters.OrdersAdapter
import com.example.ease.databinding.FragmentOrdersBinding
import com.example.ease.model.OrderGeneral
import com.example.ease.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersFragment : Fragment( R.layout.fragment_orders ), View.OnClickListener {
    private lateinit var _binding : FragmentOrdersBinding
    private val binding get() = _binding
    private val ordersList = mutableListOf<OrderGeneral>()
    private lateinit var ordersAdapter : OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate( inflater, container, false )

        _binding.ibReturn.setOnClickListener( this )

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = currentSession.id

        getOrders( userId.toString() )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.ibReturn.id -> {
                findNavController().navigate( OrdersFragmentDirections.actionOrdersFragmentToProfileFragment() )
            }
        }
    }

    private fun initRecyclerView(){
        ordersAdapter = OrdersAdapter( ordersList){ id : String -> onClickOrder( id ) }
        binding.rvOrders.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvOrders.adapter = ordersAdapter
    }

    private fun onClickOrder( id : String ){
        findNavController().navigate( OrdersFragmentDirections.actionOrdersFragmentToOrderFragment( id = id ) )
    }

    private fun getOrders( id : String ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{

                val response = APIService().getOrdersFrom( id )
                activity?.runOnUiThread {
                    ordersList.clear()
                    ordersList.addAll(0, response.body)
                    ordersAdapter.notifyDataSetChanged()

                    binding.pbLoader.visibility = View.GONE
                    toggleEmptyPlaceholders( ordersList.size > 0 )
                }
            }catch ( e : Exception ){
                Log.e("API Error", e.message ?: "")
            }
        }
    }

    private fun toggleEmptyPlaceholders( active : Boolean ){
        binding.ivEmpty.visibility = if( !active ) View.VISIBLE else View.GONE
        binding.tvEmpty.visibility = if( !active ) View.VISIBLE else View.GONE
    }
}