package com.example.ease.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.databinding.FragmentAddPaymentBinding
import com.example.ease.model.PaymentModel
import com.example.ease.service.APIService
import com.example.ease.util.PaymentState
import com.example.ease.util.RegisterValidation
import com.example.ease.util.validateDecimal
import com.example.ease.util.validateName
import com.example.ease.util.validateNumber
import com.example.ease.util.validateSimple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPaymentFragment  : Fragment( R.layout.fragment_add_payment ), View.OnClickListener {
    private lateinit var _binding : FragmentAddPaymentBinding
    private val binding get() = _binding
    val args : AddPaymentFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPaymentBinding.inflate( inflater, container, false )

        _binding.btnCreate.setOnClickListener( this )
        _binding.ibReturn.setOnClickListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        if(id != null) onLoadPaymentData( id )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.btnCreate.id -> {
                binding.btnCreate.startAnimation()
                val id = args.id
                val data = PaymentModel(
                    id = "0",
                    name = binding.etName.text.toString(),
                    number = binding.etNumber.text.toString(),
                    expire = binding.etExpireDate.text.toString(),
                    zip = binding.etZipCode.text.toString(),
                    ownerId = currentSession.id,
                    owner = ""
                )

                val validation = shouldCreate( data )

                if( validation && id == null){
                    onCreatePayment( data )
                }
                else if( validation && id != null ){
                    data.id = id
                    onUpdatePayment( data )
                }
                else
                    setUpInputErrors( data )

            }
            binding.ibReturn.id ->{
                val id = args.id

                if( id != null ){
                    findNavController().navigate( AddPaymentFragmentDirections.actionAddPaymentFragmentToPaymentFragment() )
                } else
                    findNavController().popBackStack()
            }
        }
    }

    private fun onCreatePayment( paymentModel: PaymentModel ){
        CoroutineScope( Dispatchers.IO ).launch {

            try {
                val response = APIService().createPayment( paymentModel )

                activity?.runOnUiThread {
                    Toast.makeText(context, "Se ha creado un nuevo método de pago", Toast.LENGTH_SHORT).show()
                    binding.btnCreate.revertAnimation()
                    cleanInput()
                }
            } catch (e : Exception){
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    binding.btnCreate.revertAnimation()
                }
            }
        }
    }

    private fun onUpdatePayment( paymentModel: PaymentModel ){
        CoroutineScope( Dispatchers.IO ).launch{
            try{
                val response = APIService().updatePayment( paymentModel )
                activity?.runOnUiThread {
                    Toast.makeText(context, "Se ha actualizado el método de pago", Toast.LENGTH_SHORT).show()
                    binding.btnCreate.revertAnimation()
                    findNavController().navigate(AddPaymentFragmentDirections.actionAddPaymentFragmentToPaymentFragment())
                }
            }catch ( e : Exception ){
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    binding.btnCreate.revertAnimation()
                }
            }
        }
    }
    private fun onLoadPaymentData( id : String ){
        CoroutineScope( Dispatchers.IO ).launch{
            try{
                val response = APIService().getPayment( id )
                activity?.runOnUiThread {
                    if(response.body != null) fillData( response.body )
                }

            } catch (e : Exception){
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun shouldCreate( data : PaymentModel )  : Boolean{
        val nameValidation = validateName( data.name )
        val numberValidation = validateNumber( data.number )
        val expireValidation = validateSimple( data.expire )
        val zipValidation = validateNumber( data.zip )

        return nameValidation is RegisterValidation.Success &&
                numberValidation is RegisterValidation.Success &&
                expireValidation is RegisterValidation.Success &&
                zipValidation is RegisterValidation.Success
    }

    private fun setUpInputErrors( data: PaymentModel ){
        val validation = PaymentState(
            validateName( data.name ),
            validateNumber( data.number ),
            validateSimple( data.expire ),
            validateNumber( data.zip )
        )

        if(validation.name is RegisterValidation.Failed)
            renderError( binding.etName, validation.name.message )

        if(validation.number is RegisterValidation.Failed)
            renderError( binding.etNumber, validation.number.message )

        if(validation.expire is RegisterValidation.Failed)
            renderError( binding.etExpireDate, validation.expire.message)

        if( validation.zip is RegisterValidation.Failed )
            renderError( binding.etZipCode, validation.zip.message)
    }

    private fun fillData( paymentModel: PaymentModel ){
        binding.etName.setText( paymentModel.name )
        binding.etNumber.setText( paymentModel.number )
        binding.etExpireDate.setText( paymentModel.expire )
        binding.etZipCode.setText( paymentModel.zip )
        binding.btnCreate.text = "Edit"
    }

    private fun cleanInput(){
        binding.etName.setText("")
        binding.etNumber.setText("")
        binding.etExpireDate.setText("")
        binding.etZipCode.setText("")
    }

    private fun renderError(editText : EditText, errorMessage : String){
        editText.apply {
            requestFocus()
            error = errorMessage
        }
    }
}