package com.gustavovillada.icesistappsoma.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gustavovillada.icesistappsoma.model.Modification


class ProductsFragmentViewModel: ViewModel() {


    val db= Firebase.firestore
    val auth = Firebase.auth

    var listProductsRevision = MutableLiveData<MutableList<Modification>>()


    fun getProductsRevisionEmprendimientoDataFromFirebase(){

        val idEmprendimiento=auth.currentUser!!.uid.toString()
        val productsEmprendimientoListData = mutableListOf<Modification>()

        val query = db.collection("modifications").whereEqualTo("stateModification",Modification.MODIFICATION_STATE_WAITING).limit(5)

        query.get().addOnCompleteListener{task->
            for (document in task.result){
                val modification=document.toObject(Modification::class.java)
                 productsEmprendimientoListData.add(modification)
                    Log.e("SOLICITUDES","Cargan: ")


            }
            listProductsRevision.value=productsEmprendimientoListData

        }
    }



    var namesListByCategory=MutableLiveData<List<PairNameUid>>( )

    fun getListNameEmprendimientosByCategory(category:String) {
        val query=db.collection("emprendimientos").whereEqualTo("category",category).get()

        query.addOnCompleteListener {
                task->

            var nameListEmprendimientos= ArrayList<PairNameUid>()
            for (document in task.result){
                val name=document.getString("name").toString()
                val uid=document.getString("id").toString()
                var pairNameUid=PairNameUid(name,uid)
                nameListEmprendimientos.add(pairNameUid)
            }

            namesListByCategory.value=nameListEmprendimientos
        }
    }


    var namesProductsListByIdEmprendimiento=MutableLiveData<List<PairNameUid>>( )

    fun getListNameProductsByIdEmprendimiento(idEmprendimiento:String) {
        val query=db.collection("products").whereEqualTo("idEmprendimiento",idEmprendimiento).get()

        query.addOnCompleteListener {
                task->

            var nameListProducts= ArrayList<PairNameUid>()
            for (document in task.result){
                val name=document.getString("name").toString()
                val uid=document.getString("id").toString()
                var pairNameUid=PairNameUid(name,uid)
                nameListProducts.add(pairNameUid)
            }

            namesProductsListByIdEmprendimiento.value=nameListProducts
        }
    }

    class PairNameUid(name: String,uid:String){
        var name=name
        var uid=uid
    }



    var isDisabled = MutableLiveData<Boolean>()

    fun disabledProduct(uid:String){
        //Esta validación se genera para validar cuando no seleccionan un producto
        if(uid==""){
            isDisabled.value=false
        }else{
            db.collection("products").document(uid).update(
                mapOf(
                    Pair("active",false),
                    Pair("available",false)
                )).addOnCompleteListener {
                isDisabled.value=true
            }.addOnFailureListener {
                isDisabled.value=false
            }.addOnCanceledListener {
                isDisabled.value=false
            }
        }

    }

    var isEnabled = MutableLiveData<Boolean>()

    fun enabledProduct(uid:String){
        if(uid==""){
            isEnabled.value=false
        }else{
            db.collection("products").document(uid).update(
                mapOf(
                    Pair("active",true),
                    Pair("available",true)
                )).addOnCompleteListener {
                isEnabled.value=true
            }.addOnFailureListener {
                isEnabled.value=false
            }.addOnCanceledListener {
                isEnabled.value=false
            }
        }


    }

}