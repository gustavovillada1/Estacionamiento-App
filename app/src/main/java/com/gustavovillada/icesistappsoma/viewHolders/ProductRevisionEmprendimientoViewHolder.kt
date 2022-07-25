package com.gustavovillada.icesistappsoma.viewHolders

import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gustavovillada.icesistappsoma.R

class ProductRevisionEmprendimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_product_revision_emprendimiento: CardView = itemView.findViewById(R.id.card_product_revision_emprendimiento)
        var image_product_revision_photo: ImageView = itemView.findViewById(R.id.image_product_revision_photo)
        var ic_rechaced_item_list: ImageView = itemView.findViewById(R.id.ic_rechaced_item_list)
        var ic_acepted_item_list: ImageView = itemView.findViewById(R.id.ic_acepted_item_list)
        var ic_in_revision_item_list: ImageView = itemView.findViewById(R.id.ic_in_revision_item_list)
        var ic_edit_modification: ImageView = itemView.findViewById(R.id.ic_edit_modification)
        var ic_add_modification: ImageView = itemView.findViewById(R.id.ic_add_modification)
        var ic_remove_modification: ImageView = itemView.findViewById(R.id.ic_remove_modification)
        var text_product_revision_price: TextView = itemView.findViewById(R.id.text_product_revision_name_emprendimiento)
        var text_product_revision_state: TextView = itemView.findViewById(R.id.text_product_revision_state)
        var text_product_revision_type: TextView = itemView.findViewById(R.id.text_product_revision_type)
    }