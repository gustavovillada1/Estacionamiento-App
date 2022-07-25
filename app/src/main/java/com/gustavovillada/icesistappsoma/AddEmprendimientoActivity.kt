package com.gustavovillada.icesistappsoma

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.gustavovillada.icesistappsoma.databinding.ActivityAddEmprendimientoBinding
import com.gustavovillada.icesistappsoma.model.Emprendimiento
import com.gustavovillada.icesistappsoma.viewModel.AddEmprendimientoActivityViewModel
import java.util.*

class AddEmprendimientoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEmprendimientoBinding
    private val listCategory= listOf("Categoría","Comida","Postres","Confiteria","Detalles","Otros")

    private val addEmprendimientoActivityViewModel:AddEmprendimientoActivityViewModel by viewModels()

    private var category="Categoría"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityAddEmprendimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapteSpinnerCategories()
        onItemSelectedSpinnerCategory()
        subscribeToViewModel()

        binding.btnAddEmprendimiento.setOnClickListener {
            if (verifyFieldsCorrect()&&verifyPasswordCorrect()){
                val emprendimiento=getEmprendimiento()
                val password=binding.inputPassword1EmprendimientoAdd.text.toString()
                val email=binding.inputEmailEmprendimientoAdd.text.toString()

                addEmprendimientoActivityViewModel.registerEmailAndPassword(email,password,emprendimiento)
            }
        }
    }

    private fun adapteSpinnerCategories(){
        val adapter= ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listCategory)
        binding.spinnerCategoryAddEmprendimiento.adapter=adapter
    }

    /**
     * Método encargado de contener el onitemSelectedListener del spinner de las categorías de emprendimientos
     */
    private fun onItemSelectedSpinnerCategory() {
        binding.spinnerCategoryAddEmprendimiento.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               category=listCategory[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }    }


    private fun getEmprendimiento(): Emprendimiento {
        val name=binding.inputNameEmprendimientoAdd.text.toString()
        val photoPerfil="https://i.blogs.es/e1feab/google-fotos/1366_2000.jpg"
        val photoCover="https://i.blogs.es/e1feab/google-fotos/1366_2000.jpg"
        val description=binding.inputDescriptionEmprendimientoAdd.text.toString()
        val id=""
        val email=binding.inputEmailEmprendimientoAdd.text.toString()

        return Emprendimiento(name,"",category,photoPerfil,photoCover,description,id,"0000000000","","",true,false,0,email,0)
    }

    private fun subscribeToViewModel(){
        addEmprendimientoActivityViewModel.isRegisteredCorrect.observe(this, Observer {
            result->
            if (result){
                Toast.makeText(this,"Emprendimiento registrado exitosamente.",Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Ha ocurrido un error al registrar el emprendimiento",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun verifyFieldsCorrect():Boolean{
        val nameEmprendimiento=binding.inputNameEmprendimientoAdd.text.toString()
        val descriptionEmprendimiento=binding.inputDescriptionEmprendimientoAdd.text.toString()
        val emailEmprendimiento=binding.inputEmailEmprendimientoAdd.text.toString()

        if(nameEmprendimiento.length<2){
            binding.inputNameEmprendimientoAdd.error="Nombre inválido"
            return false
        }else if(descriptionEmprendimiento.length<2){
            binding.inputDescriptionEmprendimientoAdd.error="Descripción inválida"
            return false
        }else if(emailEmprendimiento.length<2){
            binding.inputEmailEmprendimientoAdd.error="Email inválido"
            return false
        }else if(category=="Categoría"){
            return false
        }else{
         return true
        }
    }

    private fun verifyPasswordCorrect():Boolean{
        val pass1=binding.inputPassword1EmprendimientoAdd.text.toString()
        val pass2=binding.inputPassword2EmprendimientoAdd.text.toString()

        if(pass1==pass2&&pass1.length>4){
            return true
        }else{
            binding.inputPassword2EmprendimientoAdd.error="Contraseñas no coinciden"
            return false
        }
    }
}