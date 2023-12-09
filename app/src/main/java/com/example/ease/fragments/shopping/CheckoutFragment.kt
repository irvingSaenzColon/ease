package com.example.ease.fragments.shopping

import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.adapters.PaymentSelectAdapter
import com.example.ease.databinding.FragmentCheckoutBinding
import com.example.ease.model.OrderModel
import com.example.ease.model.PaymentModel
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import com.example.ease.util.hideBottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CheckoutFragment : Fragment( R.layout.fragment_checkout ), View.OnClickListener {
    private lateinit var _binding : FragmentCheckoutBinding
    private val binding get() = _binding
    private val calendar = Calendar.getInstance()
    private val args : CheckoutFragmentArgs by navArgs()
    private lateinit var paymentAdapter : PaymentSelectAdapter
    private val paymentList = mutableListOf< PaymentModel >()
    private var paymentIndexSelected : Int = -1
    private var vehicleId = -1
    private var vehiclePrice = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        _binding.ibReturn.setOnClickListener( this )
        _binding.ibAddPayment.setOnClickListener( this )
        _binding.btnCheckout.setOnClickListener( this )
        binding.tvReturnDate.setOnClickListener( this )

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigationView()

        val userId = currentSession.id.toString()
        val vehicleId = args.id ?: return

        getPaymentsFrom( userId )
        getVehicle( vehicleId )
    }

    override fun onClick(view: View?) {
        when(view?.id ){
            binding.ibReturn.id -> {
                findNavController().popBackStack()
            }
            binding.ibAddPayment.id -> {
                findNavController().navigate( CheckoutFragmentDirections.actionCheckoutFragmentToAddPaymentFragment( id=null ) )
            }
            binding.btnCheckout.id -> {
                val returnDate = binding.tvReturnDate.text.toString()

                if(returnDate != "" && paymentIndexSelected != -1){
                    val paymentId = paymentList[ paymentIndexSelected ].id.toInt()
                    val userId = currentSession.id

                    val orderModel = OrderModel(
                        orderId = 0,
                        userId = userId,
                        carId = vehicleId,
                        paymethodId = paymentId,
                        total= vehiclePrice.toFloat(),
                        returnDate = returnDate
                    )
                    Log.i("Request", orderModel.toString())
                    onCheckout( orderModel )
                } else{
                    if(returnDate.isEmpty()){
                        Toast.makeText(context, "Debe ingresar una fecha para poder realizar el alquiler", Toast.LENGTH_SHORT).show()
                    }

                    if(paymentIndexSelected == -1 ){ Toast.makeText(context, "Debe elegir un método de pago para alquilar el auto", Toast.LENGTH_SHORT).show() }
                }

            }
            binding.tvReturnDate.id -> {
                showDatePicker()
            }
        }
    }

    private fun getVehicle( id: String ){
        CoroutineScope( Dispatchers.IO ).launch {
             try {
                 val response = APIService().getVehicle( id )
                 if(response.body != null) { activity?.runOnUiThread { fillVehicleInformation( response.body ) }
                 }
             }catch (e : Exception){
                 Log.e("API Error", e.message?:"")
             }
        }
    }

    private fun getPaymentsFrom( id: String ){
        CoroutineScope( Dispatchers.IO ).launch {
            try {
                val response = APIService().getAllMyPayments( id )
                if(response.body.isNotEmpty()){
                    activity?.runOnUiThread {
                        paymentList.clear()
                        paymentList.addAll(0, response.body)
                        paymentAdapter.notifyDataSetChanged()
                    }
                }
            }catch (e : Exception){
                Log.e("API Error", e.message?:"")
            }
        }
    }

    private fun onCheckout( orderModel: OrderModel ){
        CoroutineScope( Dispatchers.IO ).launch {
            try {
                APIService().createOrder( orderModel )
                activity?.runOnUiThread {
                    Toast.makeText(context, "Se ha creado una orden", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }catch (e : Exception){
                Log.e("API Error", e.message ?: "")
            }
        }
    }

    private fun initRecyclerView(){
        paymentAdapter = PaymentSelectAdapter( paymentList) { index: Int -> onCLickPayment(index) }
        binding.rvPayments.adapter = paymentAdapter
        binding.rvPayments.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false )
    }

    private fun onCLickPayment( index: Int){
        if(paymentIndexSelected != -1){
            paymentList[ paymentIndexSelected ].selected = false
        }
        paymentList[ index ].selected = true
        paymentIndexSelected = index

        paymentAdapter.notifyDataSetChanged()
    }

    private fun showDatePicker(){
        val datePickerDialog =
            DatePickerDialog(
                requireContext(),
                { _, year :Int, month : Int, day : Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set( year, month, day )

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault() )
                    val formattedDate = dateFormat.format( selectedDate.time )
                    binding.tvReturnDate.text = formattedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

        datePickerDialog.show()
    }

    private fun fillVehicleInformation(vehicleModel: VehicleModel){
        vehicleId = vehicleModel.id
        vehiclePrice = vehicleModel.price.toDouble()
        val vehiclePrice = vehicleModel.price
        binding.tvCarName.text = vehicleModel.name
        binding.tvCarInfo.text = vehicleModel.info
        binding.tvCarModel.text = vehicleModel.model
        binding.tvCarRentPerDay.text = "rent per day $ $vehiclePrice"
        binding.tvTotalPrice.text = "$ $vehiclePrice"

        if( vehicleModel.images.isNotEmpty() ){
            val decodedString = Base64.decode( vehicleModel.images[0].image, Base64.DEFAULT )
            val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )
            binding.ivCar.setImageBitmap( decodedByte )
        }
    }

}