package com.gym.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gym.MainActivity
import com.gym.R

class NotificationHelper(var context: Context,var icon: Int, var title: String,var msg: String) {
    private val CHANNEL_ID = "Thông Báo"
    private val NOTIFICATION_ID = 123
    fun Notification(){
        createNotificationChannel()
        val senInt = Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivities(context,0, arrayOf(senInt),0)
        //------set notification dialog----
        val icon = BitmapFactory.decodeResource(context.resources, icon)
        val isNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(msg)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,isNotification)
        //---------------------------------
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = CHANNEL_ID
            val descrip = "Channel descrip"
            val imports = NotificationManager.IMPORTANCE_DEFAULT
            val cannels = NotificationChannel(CHANNEL_ID,name,imports).apply {
                description = descrip
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(cannels)
        }
    }
}