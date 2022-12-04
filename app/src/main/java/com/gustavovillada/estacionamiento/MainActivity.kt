package com.gustavovillada.estacionamiento

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat
import com.gustavovillada.estacionamiento.databinding.ActivityMainBinding
import com.gustavovillada.estacionamiento.model.Zona
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager

import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Regions
import java.util.*
import androidx.core.content.PackageManagerCompat.LOG_TAG

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import java.lang.Exception
import androidx.core.content.PackageManagerCompat.LOG_TAG

import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback

import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import java.io.UnsupportedEncodingException
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.core.content.PackageManagerCompat.LOG_TAG
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var zona: Zona


    // --- Constants to modify per your configuration ---
    // Customer specific IoT endpoint
    // AWS Iot CLI describe-endpoint call returns: XXXXXXXXXX.iot.<region>.amazonaws.com,
    private val CUSTOMER_SPECIFIC_ENDPOINT = "CHANGE_ME"

    // Cognito pool ID. For this app, pool needs to be unauthenticated pool with
    // AWS IoT permissions.
    private val COGNITO_POOL_ID = "us-east-1:71ac10cd-9d2b-4e40-ab31-72cfb9b5d8f3"

    // Region of AWS IoT
    private val MY_REGION: Regions = Regions.US_EAST_1


    private lateinit var mqttManager: AWSIotMqttManager
    private lateinit var clientId: String
    private lateinit var credentialsProvider: CognitoCachingCredentialsProvider




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        zona = Zona("Zona 1",30,20,"Llegar al edificio G")

        binding.btnZona1.setOnClickListener{
            var openZona= Intent(this,ParkingActivity::class.java)
            openZona.putExtra("zona",zona)
            startActivity(openZona)
        }

        clientId = UUID.randomUUID().toString()

        // Initialize the AWS Cognito credentials provider
        credentialsProvider = CognitoCachingCredentialsProvider(
            applicationContext,  // context
            COGNITO_POOL_ID,  // Identity Pool ID
            MY_REGION // Region
        )

        // MQTT Client
        mqttManager = AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_ENDPOINT)

        connect()

    }

    private fun connect(){
        try {
            mqttManager.connect(
                credentialsProvider
            ) { status, throwable ->
                Log.d("LOG_TAG", "Status = $status")
                runOnUiThread {
                    if (status == AWSIotMqttClientStatus.Connecting) {
                        binding.tvStatus.visibility= View.VISIBLE
                        binding.tvStatus.setText("Connecting...")
                    } else if (status == AWSIotMqttClientStatus.Connected) {
                        binding.tvStatus.visibility= View.GONE
                        binding.tvStatus.setText("Connected")
                        subscribe()
                    } else if (status == AWSIotMqttClientStatus.Reconnecting) {
                        binding.tvStatus.visibility= View.VISIBLE

                        if (throwable != null) {
                            Log.e("LOG_TAG", "Connection error.", throwable)
                        }
                        binding.tvStatus.setText("Reconnecting")
                    } else if (status == AWSIotMqttClientStatus.ConnectionLost) {
                        binding.tvStatus.visibility= View.VISIBLE

                        if (throwable != null) {
                            Log.e("LOG_TAG", "Connection error.", throwable)
                            throwable.printStackTrace()
                        }
                        binding.tvStatus.setText("Disconnected")

                    } else {
                        binding.tvStatus.visibility= View.VISIBLE

                        binding.tvStatus.setText("Disconnected")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LOG_TAG", "Connection error.", e)
        }
    }


    private fun subscribe(){


        val topic = "parqueadero/zonas/zona_1"

        Log.d("LOG_TAG", "topic = $topic")

        try {
            mqttManager.subscribeToTopic(
                topic, AWSIotMqttQos.QOS0
            ) { topic, data ->
                runOnUiThread {
                    try {
                        val message = String(data, Charset.forName("UTF-8"))
                        Log.d("LOG_TAG", "Message arrived:")
                        Log.d("LOG_TAG", "   Topic: $topic")
                        Log.d("LOG_TAG", " Message: $message")
                        //tvLastMessage.setText(message)
                    } catch (e: UnsupportedEncodingException) {
                        Log.e("LOG_TAG", "Message encoding error.", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LOG_TAG", "Subscription error.", e)
        }
    }

    private fun publish(){
        val topic: String = "escribir/topic/aca"
        val msg: String = "Mensaje"

        try {
            mqttManager.publishString(msg, topic, AWSIotMqttQos.QOS0)
        } catch (e: Exception) {
            Log.e("LOG_TAG", "Publish error.", e)
        }
    }

    private fun disconnect(){
        try {
            mqttManager.disconnect()
        } catch (e: Exception) {
            Log.e("LOG_TAG", "Disconnect error.", e)
        }

    }


}