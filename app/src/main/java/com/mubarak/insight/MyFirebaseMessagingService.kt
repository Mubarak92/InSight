package com.mubarak.insight

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mubarak.insight.activitys.MainActivity

const val channelId = "notification_channel"

//packeg name -> channel name
const val channelName = "com.mubarak.insight"


class MyFirebaseMessagingService  : FirebaseMessagingService() {

    //1) generate notification
    @SuppressLint("UnspecifiedImmutableFlag")
    fun genNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )
        //1.1) channel id,name

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, channelId
        )
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        // 2) attach notification to layout
        builder = builder.setContent(getRemoteView(title, body))

        //3.1)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(0, builder.build())
    }

    // 2.1) remoteView fun
    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, body: String): RemoteViews {
        val remoteview = RemoteViews("com.mubarak.insight", R.layout.notification)
        remoteview.setTextViewText(R.id.title, title)
        remoteview.setTextViewText(R.id.body, body)
        remoteview.setImageViewResource(R.id.app_logo, R.drawable.logo)
        return remoteview
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification() != null) {
            genNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        }


    }
}