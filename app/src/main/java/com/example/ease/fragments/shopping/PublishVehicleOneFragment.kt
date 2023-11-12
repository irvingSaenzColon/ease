package com.example.ease.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.R
import com.example.ease.activities.CarsShoppingActivity.Companion.vehicleData
import com.example.ease.databinding.FragmentPublishVehicle1Binding
import com.example.ease.model.CategoryModel
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import com.example.ease.util.CarFieldStateOne
import com.example.ease.util.RegisterValidation
import com.example.ease.util.validateDecimal
import com.example.ease.util.validateSimple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PublishVehicleOneFragment : Fragment(R.layout.fragment_publish_vehicle_1), View.OnClickListener, AdapterView.OnItemClickListener {
    private var _binding : FragmentPublishVehicle1Binding ? = null
    private val binding get() = _binding!!
    private var model : String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublishVehicle1Binding.inflate( inflater, container, false )

        binding.btnNext.setOnClickListener( this )
        binding.ibClose.setOnClickListener( this )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
        fillData()
    }


    override fun onClick(view: View?) {
        when(view?.id){
            binding.btnNext.id -> {
                val vehicleModel = VehicleModel(
                    id = 0,
                    name = binding.etName.text.toString(),
                    price = binding.etPrice.text.toString(),
                    model = model,
                    info = binding.etInfo.text.toString()
                )
                if(shouldContinue( vehicleModel )){
                    binding.tvAutoComplete.error = null
                    vehicleData = vehicleModel
                    findNavController().navigate( PublishVehicleOneFragmentDirections.actionPublishVehicleOneFragmentToPublishVehicleTwoFragment() )
                } else{
                    val validation = CarFieldStateOne(
                        validateSimple( vehicleModel.name ),
                        validateDecimal( vehicleModel.price.toString() ),
                        validateSimple( vehicleModel.model )
                    )

                    if(validation.name is RegisterValidation.Failed){
                        renderEditTextError( binding.etName, validation.name.message )
                    }
                    if(validation.price is RegisterValidation.Failed){
                        renderEditTextError( binding.etPrice, validation.price.message )
                    }
                    if(validation.model is RegisterValidation.Failed){
                        renderDropDownError( binding.tvAutoComplete, validation.model.message )
                    }

                }

            }
            binding.ibClose.id->{
                findNavController().navigate( PublishVehicleOneFragmentDirections.actionPublishVehicleOneFragmentToCartFragment() )
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition( position ).toString()
        model = item
    }

    private fun fillData(){
        binding.etName.setText( vehicleData.name )
        binding.etPrice.setText( vehicleData.price )
        binding.etInfo.setText( vehicleData.info )
        model = vehicleData.model
        binding.tvAutoComplete.setText( vehicleData.model )
    }

    private fun getCategories(){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                val response = APIService().getCategories()
                activity?.runOnUiThread{
                    val categoriesList = getArrCategories( response.body )
                    val arrAdapter = ArrayAdapter( requireContext(), R.layout.model_item, categoriesList)
                    binding.tvAutoComplete.setAdapter( arrAdapter )
                    binding.tvAutoComplete.onItemClickListener = this@PublishVehicleOneFragment
                }
                Log.i("Categories", response.body.size.toString() )
            }catch( e : Exception){
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getArrCategories(categoryArray : List<CategoryModel>) : List< String >{
        val newArr = mutableListOf<String>()

        categoryArray.forEach{category ->
            newArr.add( category.name )
        }

        return newArr
    }

    private fun shouldContinue( vehicleModel: VehicleModel ) : Boolean{
        val validateName = validateSimple( vehicleModel.name )
        val validateModel= validateSimple( vehicleModel.model )
        val validatePrice = validateDecimal( vehicleModel.price.toString() )

        return validateModel is RegisterValidation.Success &&
                validateName is RegisterValidation.Success &&
                validatePrice is RegisterValidation.Success
    }

    private fun renderEditTextError(editText : EditText, errorMessage : String ){
        editText.apply {
            requestFocus()
            error=errorMessage
        }
    }

    private fun renderDropDownError( autoCompleteTextView: AutoCompleteTextView, errorMessage: String  ){
        autoCompleteTextView.apply {
            requestFocus()
            error= errorMessage
        }
    }


}


