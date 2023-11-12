package com.example.ease.fragments.shopping

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ease.R
import com.example.ease.databinding.FragmentPublishVehicle2Binding
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ease.activities.CarsShoppingActivity.Companion.currentSession
import com.example.ease.activities.CarsShoppingActivity.Companion.vehicleData
import com.example.ease.adapters.ImagesSelectedAdapter
import com.example.ease.model.ImageModel
import com.example.ease.model.ImageSelectedModel
import com.example.ease.model.VehicleModel
import com.example.ease.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class PublishVehicleTwoFragment : Fragment( R.layout.fragment_publish_vehicle_2 ), View.OnClickListener {
    private var _binding : FragmentPublishVehicle2Binding ? = null
    private val binding get() = _binding!!
    private lateinit var imagesSelectedAdapter : ImagesSelectedAdapter
    private var currentIndexSelected : Int = -1

    private val imagesSelectedList = mutableListOf<ImageSelectedModel>()

    private val pickMedia = registerForActivityResult(PickVisualMedia()){ uri ->
        val bitmap : Bitmap

        if(uri != null && Build.VERSION.SDK_INT >= 28){

            val bytes = requireActivity().contentResolver.openInputStream( uri )?.readBytes()
            val source = ImageDecoder.createSource( requireContext().contentResolver, uri )
            bitmap = ImageDecoder.decodeBitmap( source )
            val base64 = "data:image/png;base64,"+Base64.encodeToString( bytes, Base64.DEFAULT )
            imagesSelectedList.add( ImageSelectedModel(id = 0, image = base64, bitmap= bitmap) )

            binding.ivCurrentSelected.setImageBitmap( bitmap )

            if( binding.ibRemoveImage.visibility == View.GONE ){
                binding.ibRemoveImage.visibility = View.VISIBLE
            }
            currentIndexSelected = imagesSelectedList.size - 1
            imagesSelectedAdapter.notifyItemInserted( imagesSelectedList.size -1 )
        } else if (uri != null && Build.VERSION.SDK_INT < 28){
            val stream = ByteArrayOutputStream()
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            bitmap.compress( Bitmap.CompressFormat.JPEG, 80, stream )
            val byteArray = stream.toByteArray()
            val base64 ="data:image/png;base64,"+Base64.encodeToString( byteArray, Base64.DEFAULT )

            imagesSelectedList.add( ImageSelectedModel(id = 0, image = base64, bitmap= bitmap) )

            binding.ivCurrentSelected.setImageBitmap( bitmap )

            if( binding.ibRemoveImage.visibility == View.GONE ){
                binding.ibRemoveImage.visibility = View.VISIBLE
            }

            currentIndexSelected = imagesSelectedList.size - 1
            imagesSelectedAdapter.notifyItemInserted( imagesSelectedList.size -1 )
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublishVehicle2Binding.inflate( inflater, container, false )

        binding.btnPrevious.setOnClickListener( this )
        binding.btnClose.setOnClickListener( this )
        binding.btnAddImage.setOnClickListener( this )
        binding.btnPublish.setOnClickListener( this )
        binding.ibRemoveImage.setOnClickListener( this )

        initRecyclerView()

        return binding.root
    }

    override fun onClick(view: View?) {
        when( view?.id ){
            binding.btnPrevious.id->{
                findNavController().navigate( PublishVehicleTwoFragmentDirections.actionPublishVehicleTwoFragmentToPublishVehicleOneFragment() )
            }
            binding.btnClose.id->{
                vehicleData = VehicleModel()
                findNavController().navigate( PublishVehicleTwoFragmentDirections.actionPublishVehicleTwoFragmentToCartFragment() )
            }
            binding.btnAddImage.id->{
                pickMedia.launch( PickVisualMediaRequest( PickVisualMedia.ImageOnly ) )
            }
            binding.ibRemoveImage.id->{
                onImageRemove( )
            }
            binding.btnPublish.id->{
                if( shouldCreate() ){
                    binding.btnPublish.startAnimation()
                    vehicleData.images = getImagesSelected( imagesSelectedList )
                    vehicleData.owner = currentSession.id

                    createVehicle( vehicleData )
                }else{
                    Toast.makeText(context, "You must select at least one image ${imagesSelectedList.size}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView(){

         binding.rvImagesSelected.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        imagesSelectedAdapter = ImagesSelectedAdapter( imagesSelectedList
        ) { imageSelected -> onItemSelected(imageSelected) }
        binding.rvImagesSelected.adapter = imagesSelectedAdapter

    }
    private fun onItemSelected( imageSelected: ImageSelectedModel ){
        currentIndexSelected = imagesSelectedList.indexOf( imageSelected )
        binding.ivCurrentSelected.setImageBitmap( imageSelected.bitmap )

    }
    private fun onImageRemove( ){
        val position = currentIndexSelected
        var newPosition : Int


        if( (imagesSelectedList.size - 1) <= 0 ){
            binding.ibRemoveImage.visibility = View.GONE
            binding.ivCurrentSelected.setImageResource(R.drawable.bk_rounded_corners)
        } else if(position == 0 ){
            newPosition = position + 1
            binding.ivCurrentSelected.setImageBitmap( imagesSelectedList[ newPosition ].bitmap )

        } else if(position <= ( imagesSelectedList.size - 1 )){
            newPosition = position - 1
            currentIndexSelected = newPosition
            binding.ivCurrentSelected.setImageBitmap( imagesSelectedList[ newPosition ].bitmap )
        } else{
            currentIndexSelected -= 1
        }

        imagesSelectedList.removeAt( position );

        imagesSelectedAdapter.notifyItemRemoved(position)


    }
    private fun shouldCreate() : Boolean{
        return imagesSelectedList.size > 0
    }

    private fun getImagesSelected(imagesSelected : List<ImageSelectedModel>) : List<ImageModel>{
        val newImages = mutableListOf<ImageModel>()

        imagesSelected.forEach{img ->
            newImages.add( ImageModel(0, image =  img.image) )
        }

        return newImages.toList()
    }

    private fun createVehicle( vehicleModel: VehicleModel ){
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                APIService().createVehicle( vehicleModel )

                activity?.runOnUiThread {
                    binding.btnPublish.revertAnimation()
                    Toast.makeText(context, "Vehicle has been created", Toast.LENGTH_SHORT).show()
                }
            } catch (e : Exception){
                activity?.runOnUiThread {
                    binding.btnPublish.revertAnimation()
                    Log.e("Error API", e.toString())
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}