package com.gustavovillada.icesistapp.main.fcm

data class FCMOrder<T:Any>(
    var to: String = "",
    var data : T? = null
)