package com.gustavovillada.icesistappsoma

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gustavovillada.icesistappsoma.databinding.ActivityModificationBinding
import com.gustavovillada.icesistappsoma.model.Modification
import com.gustavovillada.icesistappsoma.model.Product
import com.gustavovillada.icesistappsoma.viewModel.ModificationActivityViewModel
import java.text.DecimalFormat

class ModificationActivity : AppCompatActivity() {


    private lateinit var modification: Modification

    private lateinit var binding: ActivityModificationBinding
    private val modificationActivityViewModel: ModificationActivityViewModel by viewModels()

    var removeItemRecyler = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityModificationBinding.inflate(layoutInflater)
        var view=binding.root
        setContentView(view)

        modification=intent?.extras?.getSerializable("modification") as Modification
        adapteInformationModification(modification)
        subscribeToViewModelForResult()

        modificationActivityViewModel.productModification.observe(this, androidx.lifecycle.Observer {
            product->
            addCurrentInformationProductToEdit(product)
        })

        binding.btnBackProductActivity.setOnClickListener {
            finish()
        }

        binding.btnAceptModificationActivity.setOnClickListener {
            if(modification.typeModification==Modification.MODIFICATION_TYPE_ADD_PRODUCT){
                modificationActivityViewModel.toAddProductInFirebase(modification)
            }else if(modification.typeModification==Modification.MODIFICATION_TYPE_MODIFIED_PRODUCT){
                modificationActivityViewModel.toEditProductInFirebase(modification)
            }else  if(modification.typeModification==Modification.MODIFICATION_TYPE_REMOVE_PRODUCT){
                modificationActivityViewModel.toRemoveProductInFirebase(modification)
            }
        }

        binding.btnDeleteModificationActivity.setOnClickListener {
            modificationActivityViewModel.toCancelModification(modification)
        }
    }

    private fun subscribeToViewModelForResult(){
        modificationActivityViewModel.isAcepted.observe(this, Observer {
            result->
            if(result){
                Toast.makeText(this,"Modificación aceptada.",Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Ha ocurrido un error al aceptar esta modificación",Toast.LENGTH_LONG).show()
            }
        })

        modificationActivityViewModel.isCanceled.observe(this, Observer {
            result->
            if(result){
                Toast.makeText(this,"Modificación aceptada.",Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Ha ocurrido un error al rechazar esta modificación",Toast.LENGTH_LONG).show()

            }
        })


    }


    /**
     * Método encargado de adaptar en la vista la información del producto abierto.
     * @param modification Producto a adaptar en la vista.
     */
    private fun adapteInformationModification(modification: Modification){
        val df = DecimalFormat("$ #,###.###")

        binding.apply {
            textPriceProductActivity.text="${df.format(modification.price)}"
            textDescriptionProductActivity.text=modification.description
            textNameProductActivity.text=modification.name
            textEmprendimientoName.text=modification.nameEmprendimiento
            textModificationType.text=getModificationType(modification.typeModification)
        }

        Glide.with(this).load(modification.photoProduct).into(binding.imagePhotoProductProductactivity)
        Glide.with(this).load(modification.photoEmprendimiento).into(binding.imageEmprendimientoPhoto)

        if(modification.typeModification==Modification.MODIFICATION_TYPE_MODIFIED_PRODUCT){
            modificationActivityViewModel.getProductDataFromFirebase(modification.idProduct)
        }else{
            binding.apply {
                textPriceProductActivity2.visibility= View.GONE
                textDescriptionProductActivity2.visibility=View.GONE
                textNameProductActivity2.visibility=View.GONE
            }
        }
    }

    private fun addCurrentInformationProductToEdit(product: Product){
        val df = DecimalFormat("$ #,###.###")
        binding.apply {
            textPriceProductActivity2.text="${df.format(product.price)}"
            textDescriptionProductActivity2.text=product.description
            textNameProductActivity2.text=product.name

            textPriceProductActivity2.setTextColor(Color.LTGRAY)
            textDescriptionProductActivity2.setTextColor(Color.LTGRAY)
            textNameProductActivity2.setTextColor(Color.LTGRAY)

            textPriceProductActivity.setTextColor(Color.rgb(0,0,0))
            textDescriptionProductActivity.setTextColor(Color.rgb(0,0,0))
            textNameProductActivity.setTextColor(Color.rgb(0,0,0))

        }
    }

    private fun getModificationType(typeModification: Int): CharSequence? {

        var type=""
        when(typeModification){
            Modification.MODIFICATION_TYPE_ADD_PRODUCT-> {
                type="Agregar"
            }
            Modification.MODIFICATION_TYPE_MODIFIED_PRODUCT->{
                type="Modificar"
            }
            Modification.MODIFICATION_TYPE_REMOVE_PRODUCT->{
                type="Eliminar"
            }
        }
        return type
    }


}