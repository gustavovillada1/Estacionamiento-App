package com.gustavovillada.icesistappsoma.viewHolders

import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.gustavovillada.icesistappsoma.R

class ProductRevisionEmprendimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_product_revision_emprendimiento: CardView = itemView.findViewById(R.id.card_product_revision_emprendimiento)
        var layout_product_revision_not_available: RelativeLayout = itemView.findViewById(R.id.layout_product_revision_not_available)
        var image_product_revision_photo: ImageView = itemView.findViewById(R.id.image_product_revision_photo)
        var ic_rechaced_item_list: ImageView = itemView.findViewById(R.id.ic_rechaced_item_list)
        var ic_acepted_item_list: ImageView = itemView.findViewById(R.id.ic_acepted_item_list)
        var ic_in_revision_item_list: ImageView = itemView.findViewById(R.id.ic_in_revision_item_list)
        var text_product_revision_name_top: TextView = itemView.findViewById(R.id.text_product_revision_name_top)
        var text_product_revision_description_top: TextView = itemView.findViewById(R.id.text_product_revision_description_top)
        var text_product_revision_price: TextView = itemView.findViewById(R.id.text_product_revision_price)
        var text_product_revision_state: TextView = itemView.findViewById(R.id.text_product_revision_state)
        var text_product_revision_type: TextView = itemView.findViewById(R.id.text_product_revision_type)
    }