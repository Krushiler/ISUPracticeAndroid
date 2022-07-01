package com.bravedeveloper.sandbase.util.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.presentation.mainactivity.MainActivity
import kotlin.random.Random

class NotificationUtil(private val context: Context) { 
    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(title: String, message: String, notificationData: Bundle) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtras(notificationData)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = context.getString(R.string.notification_channel_id_default)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            color = ContextCompat.getColor(context, R.color.orange_dark)
            setSmallIcon(R.drawable.icon_notification)
            setContentTitle(title)
            setContentText(message)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_HIGH
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.notification_channel_id_default_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Random.nextInt(), notificationBuilder.build())

    }

    companion object {
        fun getNotificationAction(notificationExtras: Bundle): NotificationAction {
            val action = notificationExtras.getString(KEY_ACTION)
            if (action?.contains(VAL_ACTION_ORDER_DETAILS) == true) {
                val orderNumberString = notificationExtras.getString(KEY_ORDER_NUMBER)
                orderNumberString?.toInt()?.let { number ->
                    return NotificationAction.OrderDetails(number)
                }
            }

            return NotificationAction.NoAction
        }
    }
}