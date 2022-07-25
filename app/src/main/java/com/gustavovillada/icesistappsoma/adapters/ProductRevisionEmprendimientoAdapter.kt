package com.gustavovillada.icesistappsoma.adapters


import android.content.Intent
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gustavovillada.icesistappsoma.ModificationActivity
import com.gustavovillada.icesistappsoma.R
import com.gustavovillada.icesistappsoma.model.Modification
import com.gustavovillada.icesistappsoma.viewHolders.ProductRevisionEmprendimientoViewHolder
import java.text.DecimalFormat

class ProductRevisionEmprendimientoAdapter: RecyclerView.Adapter<ProductRevisionEmprendimientoViewHolder>() {

    private var productsList = mutableListOf<Modification>()

    fun setProductsListData(data: MutableList<Modification>){
        productsList=data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRevisionEmprendimientoViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list_product_revision_emprendimiento,parent,false)
        val productRevisionEmprendimientoViewHolder = ProductRevisionEmprendimientoViewHolder(view)
        return productRevisionEmprendimientoViewHolder
    }


    override fun onBindViewHolder(holder: ProductRevisionEmprendimientoViewHolder, position: Int) {
        val modificationN = productsList[position]

        holder.text_product_revision_price.text = modificationN.nameEmprendimiento
        Glide.with(holder.itemView.context).load(modificationN.photoEmprendimiento)
            .into(holder.image_product_revision_photo)

        holder.apply {
            ic_acepted_item_list.visibility=View.GONE
            ic_in_revision_item_list.visibility=View.GONE
            ic_rechaced_item_list.visibility=View.GONE
            ic_add_modification.visibility=View.GONE
            ic_edit_modification.visibility=View.GONE
            ic_remove_modification.visibility=View.GONE

        }

        holder.card_product_revision_emprendimiento.setOnClickListener {
            val openModification = Intent(holder.itemView.context,ModificationActivity::class.java).apply {
                putExtra("modification",modificationN)
            }
            holder.itemView.context.startActivity(openModification)
        }

        if(modificationN.typeModification==Modification.MODIFICATION_TYPE_ADD_PRODUCT){
            holder.ic_add_modification.visibility=View.VISIBLE
            holder.text_product_revision_type.text="Agregar"
        }else if(modificationN.typeModification==Modification.MODIFICATION_TYPE_MODIFIED_PRODUCT){
            holder.ic_edit_modification.visibility=View.VISIBLE
            holder.text_product_revision_type.text="Editar"
        }else if(modificationN.typeModification==Modification.MODIFICATION_TYPE_REMOVE_PRODUCT){
            holder.ic_remove_modification.visibility=View.VISIBLE
            holder.text_product_revision_type.text="Eliminar"
        }



        if(modificationN.stateModification==Modification.MODIFICATION_STATE_WAITING){
            holder.ic_in_revision_item_list.visibility=View.VISIBLE
            holder.text_product_revision_state.text="En revisión"
        }else if(modificationN.stateModification==Modification.MODIFICATION_STATE_ACEPTED){
            holder.ic_acepted_item_list.visibility=View.VISIBLE
            holder.text_product_revision_state.text="Aceptado"
        }else if(modificationN.stateModification==Modification.MODIFICATION_STATE_DENEGATED){
            holder.apply {
                ic_rechaced_item_list.visibility=View.VISIBLE
                text_product_revision_state.text="Rechazado"
            }
        }

    }

    fun addProductRevision(modification: Modification){

        productsList.add(modification)
        notifyItemInserted(productsList.size-1)
    }

    fun deleteProduct(position:Int){
        productsList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear(){
        productsList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return  productsList.size

    }

}


