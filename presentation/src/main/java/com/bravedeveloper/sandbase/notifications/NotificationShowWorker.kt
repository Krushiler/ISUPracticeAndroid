package com.bravedeveloper.sandbase.notifications

import android.content.Context
import android.os.Bundle
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bravedeveloper.sandbase.util.notification.NotificationUtil

class NotificationShowWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams){

    override fun doWork(): Result {

        val title = inputData.getString(NOTIFICATION_TITLE)
        val message = inputData.getString(NOTIFICATION_MESSAGE)
        val keys = inputData.getStringArray(NOTIFICATION_DATA_KEYS)

        val notificationData = Bundle()
        if (keys != null) {
            for (key in keys) {
                val value = inputData.getString(key)
                value?.let {
                    notificationData.putString(key, it)
                }
            }
        }

        if (title != null && message != null) {
            NotificationUtil(applicationContext).showNotification(title, message, notificationData)
            return Result.success()
        }

        return  Result.failure()
    }

    companion object {
        const val NOTIFICATION_TITLE = "notification_title"
        const val NOTIFICATION_MESSAGE = "notification_message"
        const val NOTIFICATION_DATA_KEYS = "notification_data_keys"
    }

}