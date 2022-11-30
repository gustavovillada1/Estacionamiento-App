package com.gustavovillada.icesistapp.main.utils

import java.net.URL
import javax.net.ssl.*


class HTTPSWebUtilDomi {

    fun GETRequest(url: String): String {
        val url = URL(url)
        val client = url.openConnection() as HttpsURLConnection
        client.requestMethod = "GET"
        return client.inputStream.bufferedReader().readText()
    }

    fun POSTRequest(url: String, json: String): String {
        val url = URL(url)
        val client = url.openConnection() as HttpsURLConnection
        client.requestMethod = "POST"
        client.setRequestProperty("Content-Type", "application/json")
        client.doOutput = true
        client.outputStream.bufferedWriter().use {
            it.write(json)
            it.flush()
        }
        return client.inputStream.bufferedReader().readText()
    }

    fun POSTtoFCM(json: String): String {
        val url = URL("https://fcm.googleapis.com/fcm/send")
        val client = url.openConnection() as HttpsURLConnection
        client.requestMethod = "POST"
        client.setRequestProperty("Content-Type", "application/json")
        client.setRequestProperty("Authorization", "key=$FCM_KEY")
        client.doOutput = true
        client.outputStream.bufferedWriter().use {
            it.write(json)
            it.flush()
        }
        val res = client.inputStream.bufferedReader().readText()
        return res
    }

    fun PUTRequest(url: String, json: String): String {
        val url = URL(url)
        val client = url.openConnection() as HttpsURLConnection
        client.requestMethod = "PUT"
        client.setRequestProperty("Content-Type", "application/json")
        client.doOutput = true
        client.outputStream.bufferedWriter().use {
            it.write(json)
            it.flush()
        }
        return client.inputStream.bufferedReader().readText()
    }

    fun DELETERequest(url: String): String {
        val url = URL(url)
        val client = url.openConnection() as HttpsURLConnection
        client.requestMethod = "DELETE"
        return client.inputStream.bufferedReader().readText()
    }


    companion object {
                                  //AAAA4SL_1t0:APA91bGl5sYx9JbQX8QxMSYmLsOKGt_7SGEWP9owDrdF4MuZr9a4cZZBMJSYXA5Ry9sH0AY215Oc_iJ0IbxVQTsqkScr5Tn7k1G4Dzm97UVtupEIafFktin-YMfgyZ8NMFbeSojPgc2c
        const val FCM_KEY:String = "AAAA4SL_1t0:APA91bGl5sYx9JbQX8QxMSYmLsOKGt_7SGEWP9owDrdF4MuZr9a4cZZBMJSYXA5Ry9sH0AY215Oc_iJ0IbxVQTsqkScr5Tn7k1G4Dzm97UVtupEIafFktin-YMfgyZ8NMFbeSojPgc2c"
    }



}