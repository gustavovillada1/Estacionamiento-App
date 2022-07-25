package com.gustavovillada.icesistappsoma.viewModel

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gustavovillada.icesistappsoma.MainActivity


class LoginActivityViewModel: ViewModel() {


    val db= Firebase.firestore
    val auth = Firebase.auth




    var isSigned=MutableLiveData<Boolean>( )

    fun sigInWithEmail(email:String,password:String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                verifyAccessAccount(auth.currentUser!!.uid)
            }.addOnCanceledListener {
                isSigned.value=false
            }.addOnFailureListener {
                isSigned.value=false
            }
    }


    private fun verifyAccessAccount(uidLoged: String){
        val query = db.collection("applicationSettings").document("soma").collection("accessAccount").get()

        var foundUidInAccessAccount=false

        query.addOnCompleteListener {
            task->
            for(document in task.result){
                val uidFirebase = document.getString("uid")
                if (uidFirebase==uidLoged){
                    foundUidInAccessAccount=true
                }
            }
            isSigned.value=foundUidInAccessAccount
        }
    }


}