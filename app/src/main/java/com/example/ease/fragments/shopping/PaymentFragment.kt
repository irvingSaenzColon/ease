package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.adapters.PaymentEditAdapter
import com.example.ease.databinding.FragmentPaymentBinding
import com.example.ease.model.PaymentModel
import com.example.ease.service.APIService
import com.example.ease.util.hideBottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentFragment : Fragment( R.layout.fragment_payment ), View.OnClickListener {
    private lateinit var _binding : FragmentPaymentBinding
    private val binding get()= _binding
    private val paymentList = mutableListOf< PaymentModel >()
    private lateinit var paymentsAdapter : PaymentEditAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()

        _binding = FragmentPaymentBinding.inflate( inflater, container, false )

        binding.btnAddPayment.setOnClickListener( this )
        binding.imReturn.setOnClickListener( this )
        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = currentSession.id
        onLoadMyPayments( id.toString() )
    }
    override fun onClick(view: View?) {
        when(view?.id){
            binding.btnAddPayment.id -> {
                findNavController().navigate( PaymentFragmentDirections.actionPaymentFragmentToAddPaymentFragment( id = null ) )
            }
            binding.imReturn.id -> {
                findNavController().navigate( PaymentFragmentDirections.actionPaymentFragmentToProfileFragment() )
            }
        }
    }

    private fun onLoadMyPayments( id: String ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getAllMyPayments( id )
                activity?.runOnUiThread {

                    if(response.body.isNotEmpty()){
                        paymentList.clear()
                        paymentList.addAll( response.body )
                        paymentsAdapter.notifyDataSetChanged()
                    }

                    binding.pbLoader.visibility = View.GONE
                    toggleEmptyPlaceholders( paymentList.size > 0 )
                }

            } catch (e: Exception){
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    Log.i("API Error", e.message ?: "")
                }
            }
        }
    }

    private fun onDeletePayment(id : String, index : Int){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                APIService().deletePayment( id )
                activity?.runOnUiThread {
                    paymentList.removeAt( index )
                    paymentsAdapter.notifyDataSetChanged()
                    Toast.makeText(context, "Se ha eliminado el método de pago", Toast.LENGTH_SHORT).show()

                    toggleEmptyPlaceholders( paymentList.size > 0 )
                }
            } catch (e : Exception){
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView(){
        paymentsAdapter = PaymentEditAdapter( paymentList ){ view : View, int : Int, paymentModel : PaymentModel -> popUpMenu(view, int, paymentModel) }
        binding.rvPayments.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPayments.adapter = paymentsAdapter
    }

    private fun popUpMenu(view : View, index : Int, paymentModel: PaymentModel){
        val popupMenu = PopupMenu( view.context.applicationContext,  view)
        popupMenu.inflate( R.menu.pop_up_menu )

        popupMenu.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.iEditQuote ->{
                    findNavController().navigate( PaymentFragmentDirections.actionPaymentFragmentToAddPaymentFragment( id = paymentModel.id ) )
                    true
                }
                R.id.iDeleteQuote -> {
                    onDeletePayment( paymentModel.id, index )
                    true
                }
                else -> true
            }
        }
        popupMenu.show()
    }

    private fun toggleEmptyPlaceholders( active: Boolean ){
        binding.ivEmpty.visibility = if( !active ) View.VISIBLE else View.GONE
        binding.tvEmpty.visibility = if( !active ) View.VISIBLE else View.GONE
    }
}