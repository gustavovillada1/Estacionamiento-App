package com.gustavovillada.icesistappsoma.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EmprendimientosFragmentViewModel: ViewModel() {


    val db= Firebase.firestore
    val auth = Firebase.auth





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



    class PairNameUid(name: String,uid:String){
        var name=name
        var uid=uid
    }



    var isDisabled = MutableLiveData<Boolean>()

    fun disabledEmprendimiento(uid:String){
        db.collection("emprendimientos").document(uid).update(
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

    var isEnabled = MutableLiveData<Boolean>()

    fun enabledEmprendimiento(uid:String){
        db.collection("emprendimientos").document(uid).update(
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