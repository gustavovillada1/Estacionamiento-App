package com.gustavovillada.icesistapp.main.utils


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gustavovillada.estacionamiento.MainActivity
import com.gustavovillada.estacionamiento.R

object NotificationUtil {

    private val CHANNEL_ID = "messages";
    private val CHANNEL_NAME = "Messages";
    private var id = 0;

    fun showNotification(context: Context, title:String, message:String){

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }


        val intent = Intent(context, MainActivity::class.java)
        val pending = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentText(message)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pending)

        val notification = builder.build()
        notificationManager.notify(id, notification)
        id++
    }

}