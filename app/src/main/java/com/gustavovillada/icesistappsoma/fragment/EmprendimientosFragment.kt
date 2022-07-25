package com.gustavovillada.icesistappsoma.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gustavovillada.icesistappsoma.AddEmprendimientoActivity
import com.gustavovillada.icesistappsoma.R
import com.gustavovillada.icesistappsoma.databinding.FragmentEmprendimientosBinding
import com.gustavovillada.icesistappsoma.viewModel.EmprendimientosFragmentViewModel
import com.synnapps.carouselview.ImageListener


class EmprendimientosFragment : Fragment() {

    private var imagesArray:ArrayList<Int> = ArrayList()
    private val listCategory= listOf("Categoría","Comida","Postres","Confiteria","Detalles","Otros")


    private var listUidEmprendimientos= ArrayList<String>()
    private var listNamesEmprendimiento= ArrayList<String>()

    private lateinit var binding: FragmentEmprendimientosBinding

    private var emprendimientoSelected:EmprendimientosFragmentViewModel.PairNameUid = EmprendimientosFragmentViewModel.PairNameUid("","")

    private val emprendimientosFragmentViewModel: EmprendimientosFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEmprendimientosBinding.inflate(inflater,container,false)

        adapteImagesInCarousel()
        adapteSpinnerCategories()
        adapteSpinnerNamesByCategory()

        logicToDisabledEmprendimiento()
        logicToEnabledEmprendimiento()

        onItemSelectedSpinnerCategory()
        onItemSelectedSpinnerNamesEmprendimiento()

        binding.cardRegisterEmprendimiento.setOnClickListener {
            val openRegisterEmprendimiento= Intent(this.context,AddEmprendimientoActivity::class.java)
            startActivity(openRegisterEmprendimiento)
        }

        return binding.root
    }

    /**
     * Método encargado de contener el onitemSelectedListener del spinner de las categorías de emprendimientos
     */
    private fun onItemSelectedSpinnerCategory() {
        binding.spinnerCategoryEmprendimiento.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                emprendimientosFragmentViewModel.getListNameEmprendimientosByCategory(listCategory[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }    }

    /**
     * Método encargado de contener el onitemSelectedListener del spinner de los nombres de los emprendimientos según la categoría
     */
    private fun onItemSelectedSpinnerNamesEmprendimiento(){
        binding.spinnerNameEmprendimientos.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val name=listNamesEmprendimiento[position]
                val uid=listUidEmprendimientos[position]
                emprendimientoSelected= EmprendimientosFragmentViewModel.PairNameUid(name,uid)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


    private fun logicToDisabledEmprendimiento(){
        emprendimientosFragmentViewModel.isDisabled.observe(this, Observer {
                isDisabled->
            if (isDisabled){
                if(emprendimientoSelected.name!="") {
                    Toast.makeText(this.requireContext(),"¡"+emprendimientoSelected.name+" ha sido deshabilitado!",Toast.LENGTH_LONG).show()
                    clearSearchEmprendimiento()
                }
            }else{
                Toast.makeText(this.requireContext(),"Ha ocurrido un error al deshabilitar el emprendimiento.",Toast.LENGTH_SHORT).show()

            }

        })

        binding.cardDisabledEmprendimiento.setOnClickListener {
            val uidEmprendimiento=emprendimientoSelected.uid
            emprendimientosFragmentViewModel.disabledEmprendimiento(uidEmprendimiento)
        }
    }

    private fun logicToEnabledEmprendimiento(){
        emprendimientosFragmentViewModel.isEnabled.observe(this, Observer {
                isEnabled->
            if (isEnabled){
                if(emprendimientoSelected.name!="") {
                    Toast.makeText(this.requireContext(),"¡"+emprendimientoSelected.name+" ha sido habilitado!",Toast.LENGTH_LONG).show()
                    clearSearchEmprendimiento()
                }
            }else{
                Toast.makeText(this.requireContext(),"Ha ocurrido un error al habilitar el emprendimiento.",Toast.LENGTH_SHORT).show()

            }

        })

        binding.cardEnabledEmprendimiento.setOnClickListener {
            val uidEmprendimiento=emprendimientoSelected.uid
            emprendimientosFragmentViewModel.enabledEmprendimiento(uidEmprendimiento)
        }
    }

    private fun clearSearchEmprendimiento(){
        listUidEmprendimientos.clear()
        listNamesEmprendimiento.clear()
        adapteSpinnerCategories()
        adapteSpinnerNamesByCategory()
        emprendimientoSelected= EmprendimientosFragmentViewModel.PairNameUid("","")
        ocultDisabledEnabled()
    }

    private fun adapteSpinnerCategories(){
        val adapter= ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item,listCategory)
        binding.spinnerCategoryEmprendimiento.adapter=adapter
    }

    private fun adapteSpinnerNamesByCategory(){
        binding.linearBlockEmprendimiento.visibility=View.GONE
        binding.linearUnblockEmprendimiento.visibility=View.GONE

        emprendimientosFragmentViewModel.namesListByCategory.observe(this, Observer {
            list->
            listUidEmprendimientos.clear()
            listNamesEmprendimiento.clear()
            for (pair in list){
                listNamesEmprendimiento.add(pair.name)
                listUidEmprendimientos.add(pair.uid)
            }

            ocultDisabledEnabled()

            val adapter= ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item,listNamesEmprendimiento)
            binding.spinnerNameEmprendimientos.adapter=adapter
        })
    }

    /**
     * Método encargado de ocultar las cards de bloquear y desbloquear, cuando la lista de nombres esté vacía (Ocurre cuando no han seleccionado alguna categoría)
     */
    private fun ocultDisabledEnabled(){
        if(listNamesEmprendimiento.isEmpty()){
            emprendimientoSelected= EmprendimientosFragmentViewModel.PairNameUid("","")
            binding.linearBlockEmprendimiento.visibility=View.GONE
            binding.linearUnblockEmprendimiento.visibility=View.GONE
        }else{
            binding.linearBlockEmprendimiento.visibility=View.VISIBLE
            binding.linearUnblockEmprendimiento.visibility=View.VISIBLE
        }
    }


    private fun adapteImagesInCarousel(){

        imagesArray.add(R.drawable.icesi)
        imagesArray.add(R.drawable.icesi)
        imagesArray.add(R.drawable.icesi)

        binding.carouselViewProducts.pageCount=imagesArray.size
        binding.carouselViewProducts.setImageListener(imageListener)
    }


    var imageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            imageView!!.setImageResource(imagesArray[position])
        }

    }
    companion object {
        //AQUI VAN FUNCIONES Y VARIABLES ESTÁTICAS
        @JvmStatic
        fun newInstance() =
            EmprendimientosFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}