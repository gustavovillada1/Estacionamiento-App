package com.gustavovillada.icesistappemprendedores.util

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME="mySharedPreferences"
    val SHARED_PHOTO_COVER="photoCover"
    val SHARED_PHOTO_PERFIL="photoPerfil"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun savePhotoCover(photoUrl:String){
        storage.edit().putString(SHARED_PHOTO_COVER,photoUrl).apply()
    }

    fun getPhotoCover():String{
        return storage.getString(SHARED_PHOTO_COVER,"NA")!!
    }


    fun savePhotoPerfil(photoUrl:String){
        storage.edit().putString(SHARED_PHOTO_PERFIL,photoUrl).apply()
    }

    fun getPhotoPerfil():String{
        return storage.getString(SHARED_PHOTO_PERFIL,"NA")!!
    }
}