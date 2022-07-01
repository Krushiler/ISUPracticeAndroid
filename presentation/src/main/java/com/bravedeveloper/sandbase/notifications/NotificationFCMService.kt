package com.bravedeveloper.sandbase.notifications

import android.annotation.SuppressLint
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class NotificationFCMService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val notificationDataBuilder = Data.Builder()

        notificationDataBuilder.putString(NotificationShowWorker.NOTIFICATION_TITLE, message.notification?.title.toString())
        notificationDataBuilder.putString(NotificationShowWorker.NOTIFICATION_MESSAGE, message.notification?.body.toString())
        notificationDataBuilder.putAll(message.data as Map<String, Any>)
        notificationDataBuilder.putStringArray(NotificationShowWorker.NOTIFICATION_DATA_KEYS, message.data.keys.toTypedArray())

        val showNotificationWorkRequest = OneTimeWorkRequestBuilder<NotificationShowWorker>()
            .setInputData(notificationDataBuilder.build())
            .build()

        WorkManager.getInstance(applicationContext).enqueue(showNotificationWorkRequest)
    }

}