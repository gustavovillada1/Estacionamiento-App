package com.gustavovillada.icesistappsoma.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gustavovillada.icesistappsoma.model.Modification
import com.gustavovillada.icesistappsoma.model.Product


class ModificationActivityViewModel: ViewModel() {


    val db= Firebase.firestore
    val auth = Firebase.auth

    var productModification = MutableLiveData<Product>()

    fun getProductDataFromFirebase(idProduct:String){
        val query = db.collection("products").whereEqualTo("id",idProduct)

        query.get().addOnCompleteListener{task->
            for (document in task.result){
                val product=document.toObject(Product::class.java)
                productModification.value=product
                break
            }
        }
    }
    


    var isCanceled = MutableLiveData<Boolean>()

    fun toCancelModification(modification: Modification){
        try{
            db.collection("modifications").document(modification.idModification).update(
                mapOf(
                    Pair("stateModification",Modification.MODIFICATION_STATE_DENEGATED)
                )
            ).addOnCompleteListener {
                isCanceled.value=true
            }.addOnFailureListener {
                isCanceled.value=false
            }.addOnCanceledListener {
                isCanceled.value=false
            }
        }catch (e:java.lang.Exception){
            isCanceled.value=false
        }
    }


    var isAcepted = MutableLiveData<Boolean>()

    fun toEditProductInFirebase(modification: Modification){
        try{

        val product= modification.toProduct()
            db.collection("modifications").document(modification.idModification).update(
                mapOf(
                    Pair("stateModification",Modification.MODIFICATION_STATE_ACEPTED)
                )
            ).addOnCompleteListener {

                db.collection("products").document(product.id).update(

                    mapOf(
                        Pair("name",product.name),
                        Pair("description",product.description),
                        Pair("price",product.price)
                    )
                ).addOnCompleteListener {
                    this.isAcepted.value=true
                }.addOnFailureListener {
                    this.isAcepted.value=false
                }.addOnCanceledListener {
                    this.isAcepted.value=false
                }

            }.addOnFailureListener {
                this.isAcepted.value=false
            }.addOnCanceledListener {
                this.isAcepted.value=false
            }

        }catch (e:Exception){
            this.isAcepted.value=false
        }

    }


    fun toAddProductInFirebase(modification: Modification){

        val product= modification.toProduct()
        db.collection("modifications").document(modification.idModification).update(
            mapOf(
                Pair("stateModification",Modification.MODIFICATION_STATE_ACEPTED)
            )
        ).addOnCompleteListener {

            db.collection("products").document(product.id).set(
                product
            ).addOnCompleteListener {
                this.isAcepted.value=true
            }.addOnFailureListener {
                this.isAcepted.value=false
            }.addOnCanceledListener {
                this.isAcepted.value=false
            }

        }.addOnFailureListener {
            this.isAcepted.value=false
        }.addOnCanceledListener {
            this.isAcepted.value=false
        }

    }




    fun toRemoveProductInFirebase(modification: Modification){

        val product= modification.toProduct()

        db.collection("modifications").document(modification.idModification).update(
            mapOf(
                Pair("stateModification",Modification.MODIFICATION_STATE_ACEPTED)
            )
        ).addOnCompleteListener {

            db.collection("products").document(product.id).delete()
                .addOnCompleteListener {
                    this.isAcepted.value=true
                }.addOnFailureListener {
                    this.isAcepted.value=false
                }.addOnCanceledListener {
                    this.isAcepted.value=false
                }

        }.addOnFailureListener {
            this.isAcepted.value=false
        }.addOnCanceledListener {
            this.isAcepted.value=false
        }



    }


}

