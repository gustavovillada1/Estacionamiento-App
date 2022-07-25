package com.gustavovillada.icesistappsoma.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gustavovillada.icesistappsoma.R
import com.gustavovillada.icesistappsoma.adapters.ProductRevisionEmprendimientoAdapter
import com.gustavovillada.icesistappsoma.databinding.FragmentProductsBinding
import com.gustavovillada.icesistappsoma.viewModel.EmprendimientosFragmentViewModel
import com.gustavovillada.icesistappsoma.viewModel.ProductsFragmentViewModel


class ProductsFragment : Fragment() {


    private val productsFragmentViewModel: ProductsFragmentViewModel by viewModels()

    private lateinit var binding: FragmentProductsBinding
    private val listCategory= listOf("Categoría","Comida","Postres","Confiteria","Detalles","Otros")


    private var listUidEmprendimientos= ArrayList<String>()
    private var listNamesEmprendimiento= ArrayList<String>()

    private var listUidProducts= ArrayList<String>()
    private var listNamesProducts= ArrayList<String>()

    private var emprendimientoSelected: EmprendimientosFragmentViewModel.PairNameUid = EmprendimientosFragmentViewModel.PairNameUid("","")
    private var productSelected:EmprendimientosFragmentViewModel.PairNameUid = EmprendimientosFragmentViewModel.PairNameUid("","")

    private var productRevisionEmprendimientoAdapter= ProductRevisionEmprendimientoAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProductsBinding.inflate(inflater,container,false)
        val view=binding.root

        adapteRecyclerView()
        productsFragmentViewModel.getProductsRevisionEmprendimientoDataFromFirebase()

        adapteSpinnerCategories()
        adapteSpinnerNamesByCategory()
        adapteSpinnerNamesProductsByIdEmprendimiento()

        logicToDisabledProduct()
        logicToEnabledProduct()

        onItemSelectedSpinnerCategory()
        onItemSelectedSpinnerNamesEmprendimiento()
        onItemSelectedSpinnerNamesProducts()

        return view
    }


    private fun adapteRecyclerView(){
        var productsRevisionEmprendimientoRecycler = binding.recyclerModifications
        productsRevisionEmprendimientoRecycler.setHasFixedSize(true)
        productsRevisionEmprendimientoRecycler.layoutManager= LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        productsRevisionEmprendimientoRecycler.adapter=productRevisionEmprendimientoAdapter;

        productsFragmentViewModel.listProductsRevision.observe(this, Observer {
            listProductsRevision->

            Log.e("SOLICITUDES","Cargan: "+listProductsRevision.size)
            productRevisionEmprendimientoAdapter.setProductsListData(listProductsRevision)
            productRevisionEmprendimientoAdapter.notifyDataSetChanged()

        })
    }

    /**
     * Método encargado de contener el onitemSelectedListener del spinner de las categorías de emprendimientos
     */
    private fun onItemSelectedSpinnerCategory() {
        binding.spinnerCategoryEmprendimientoProducts.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                productsFragmentViewModel.getListNameEmprendimientosByCategory(listCategory[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }    }

    /**
     * Método encargado de contener el onitemSelectedListener del spinner de los nombres de los emprendimientos según la categoría
     */
    private fun onItemSelectedSpinnerNamesEmprendimiento(){
        binding.spinnerNameEmprendimientoProducts.onItemSelectedListener=object :
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

                productsFragmentViewModel.getListNameProductsByIdEmprendimiento(uid)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun onItemSelectedSpinnerNamesProducts(){
        binding.spinnerProductNameEmprendimientoProducts.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val name=listNamesProducts[position]
                val uid=listUidProducts[position]
                productSelected= EmprendimientosFragmentViewModel.PairNameUid(name,uid)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun logicToDisabledProduct(){
        productsFragmentViewModel.isDisabled.observe(this, Observer {
                isDisabled->
            if (isDisabled){
                if(productSelected.name!=""){
                        Toast.makeText(this.requireContext(),"¡"+productSelected.name+" ha sido deshabilitado!",
                        Toast.LENGTH_LONG).show()
                    clearSearchProductEmprendimiento()
                }
            }else{
                Toast.makeText(this.requireContext(),"Ha ocurrido un error al deshabilitar el producto.",
                    Toast.LENGTH_SHORT).show()

            }

        })

        binding.cardDisabledProductEmprendimiento.setOnClickListener {
            val uidProduct=productSelected.uid
            productsFragmentViewModel.disabledProduct(uidProduct)
        }
    }

    private fun logicToEnabledProduct(){
        productsFragmentViewModel.isEnabled.observe(this, Observer {
                isEnabled->
            if (isEnabled){
                if(productSelected.name!=""){
                    Toast.makeText(this.requireContext(),"¡"+productSelected.name+" ha sido habilitado!",
                        Toast.LENGTH_LONG).show()
                    clearSearchProductEmprendimiento()
                }
            }else{
                Toast.makeText(this.requireContext(),"Ha ocurrido un error al habilitar el producto.",
                    Toast.LENGTH_SHORT).show()

            }

        })

        binding.cardEnabledProductEmprendimiento.setOnClickListener {
            val uidProduct=productSelected.uid
            productsFragmentViewModel.enabledProduct(uidProduct)
        }
    }

    private fun adapteSpinnerCategories(){
        val adapter= ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item,listCategory)
        binding.spinnerCategoryEmprendimientoProducts.adapter=adapter
    }


    /**
     * Método encargado de observar en el viewModel la lista de emprendimientos por categoría para posteriormente seguir
     * la logica para colocar los nombres de los emprendimientos en el spinner.
     */
    private fun adapteSpinnerNamesByCategory(){
        //Dejamos por defecto los botones (Cards) ocultos y en la lógica siguiente se verifica si se necesitan poner visibles o no,
        //unicamente ocultamos los botones en este método y no en el spinner de productos porque cuando seleccionamos un emprendimiento automaticamente se
        //llama el metodo para adaptar el spinner de productos, entonces no es necesario validar otra vez si se necesita habilitar los botones.
        binding.linearEnabledDisabledProduct.visibility=View.GONE


        //Observer
        productsFragmentViewModel.namesListByCategory.observe(this, Observer {
                list->

            //Reseteamos la lista de emprendimientos y productos que con anterioridad han sido cargados al spinner
            listUidEmprendimientos.clear()
            listNamesEmprendimiento.clear()

            listNamesProducts.clear()
            listUidProducts.clear()

            //Cargamos la nueva lista de nombres
            for (pair in list){
                listNamesEmprendimiento.add(pair.name)
                listUidEmprendimientos.add(pair.uid)
            }

            //Este método va a verificar si es necesario
            ocultDisabledEnabled()

            val adapter= ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item,listNamesEmprendimiento)
            binding.spinnerNameEmprendimientoProducts.adapter=adapter
        })
    }

    /**
     * Método encargado de observar en el viewModel la lista de productos que tiene un emprendimiento, para posteriormente seguir la logica para colocar los nombres en el spinner.
     */
    private fun adapteSpinnerNamesProductsByIdEmprendimiento(){
    // La lista de productos se coloca automaticamente seleccionan un nombre del spinner de emprendimientos.

        productsFragmentViewModel.namesProductsListByIdEmprendimiento.observe(this, Observer {
                list->
            listNamesProducts.clear()
            listUidProducts.clear()
            for (pair in list){
                listNamesProducts.add(pair.name)
                listUidProducts.add(pair.uid)
            }

            //Si llega a un emprendimiento que no tiene productos, se resetea el ultimo seleccionado.
            if(list.isEmpty()){
                productSelected=EmprendimientosFragmentViewModel.PairNameUid("","")
            }

            ocultDisabledEnabled()

            val adapter= ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item,listNamesProducts)
            binding.spinnerProductNameEmprendimientoProducts.adapter=adapter
        })
    }


    /**
     * Método encargado de ocultar las cards de bloquear y desbloquear, cuando la lista de nombres esté vacía (Ocurre cuando no han seleccionado alguna categoría)
     */
    private fun ocultDisabledEnabled(){
        if(listNamesEmprendimiento.isEmpty()){
            //Reseteamos el emprendimiento y producto si ya ha sido seleccionado previamente
            emprendimientoSelected= EmprendimientosFragmentViewModel.PairNameUid("","")
            productSelected= EmprendimientosFragmentViewModel.PairNameUid("","")

            //Ocultamos los botones (Cards) para Habilitar o deshabilitar
            binding.linearEnabledDisabledProduct.visibility=View.GONE

            //Quitamos los elementos del spinner de productos que quedan y volvemos a colocarlo vacío
            listNamesProducts.clear()
            listUidProducts.clear()
            val adapter= ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item,listNamesProducts)
            binding.spinnerProductNameEmprendimientoProducts.adapter=adapter

        }else{

            //Habilitamos los botones (Cards) para habilitar o deshabilitar el producto
            binding.linearEnabledDisabledProduct.visibility=View.VISIBLE
        }
    }


    private fun clearSearchProductEmprendimiento(){
        //Limpiamos todas las listas de emprendimientos y productos que se buscan
        listUidEmprendimientos.clear()
        listNamesEmprendimiento.clear()
        listNamesProducts.clear()
        listUidProducts.clear()

        //Volvemos a llamar el adaptador del spinner
        adapteSpinnerCategories()
        adapteSpinnerNamesByCategory()
        adapteSpinnerNamesProductsByIdEmprendimiento()

        //Reseteamos los emprendimientos o productos seleccionados
        emprendimientoSelected= EmprendimientosFragmentViewModel.PairNameUid("","")
        productSelected= EmprendimientosFragmentViewModel.PairNameUid("","")

        //Se ocultan los botones (Cards) de habilitar o deshabilitar
        ocultDisabledEnabled()
    }

    /**
     * Este método se encarga de observar si se aceptó o canceló la modificación hecha
     */
    private fun observerModificationDone(){

    }

    override fun onStart() {
        super.onStart()
        productRevisionEmprendimientoAdapter.clear()
        productsFragmentViewModel.getProductsRevisionEmprendimientoDataFromFirebase()

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }
}