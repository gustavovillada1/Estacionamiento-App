package com.gustavovillada.icesistappsoma.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gustavovillada.icesistappsoma.model.Emprendimiento


class AddEmprendimientoActivityViewModel: ViewModel() {


    val db= Firebase.firestore
    val auth = Firebase.auth



    var isRegisteredCorrect=MutableLiveData<Boolean>()

    fun registerEmailAndPassword(email:String,password:String, emprendimiento:Emprendimiento){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                try{
                    val id=it.result.user!!.uid
                    emprendimiento.id=id
                    uploadEmprendimientoToFirebase(emprendimiento)
                }catch (e:Exception){
                    isRegisteredCorrect.value=false
                }
            }.addOnFailureListener {
                isRegisteredCorrect.value=false
            }.addOnCanceledListener {
                isRegisteredCorrect.value=false

            }

    }

    private fun uploadEmprendimientoToFirebase(emprendimiento: Emprendimiento){
        db.collection("emprendimientos").document(emprendimiento.id).set(emprendimiento).addOnCompleteListener {
            isRegisteredCorrect.value=true
        }.addOnCanceledListener {
            isRegisteredCorrect.value=false
        }.addOnFailureListener {
            isRegisteredCorrect.value=false
        }
    }


}