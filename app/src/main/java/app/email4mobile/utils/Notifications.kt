package app.email4mobile.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import app.email4mobile.R
import app.email4mobile.ui.activity.DetailEventActivity


class Notifications {


    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "app.email4mobile.utils"
    private val description = "Test notification"

    fun createNotification(context: Context) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, DetailEventActivity::class.java)
        val pendingInt = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.YELLOW
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                    .setContentTitle("Hello")
                    .setContentText("Tady problem")
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                    .setContentIntent(pendingInt)
        } else {
            builder = Notification.Builder(context)
                    .setContentTitle("Hello")
                    .setContentText("Tady problem")
                    .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                    .setContentIntent(pendingInt)
        }
        notificationManager.notify(1234, builder.build())
    }

    fun scheduleNotification(context: Context, notification: Notification, delay: Int) {

        notificationManager.notify(1234, notification)
        val notificationIntent = Intent(context, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager!!.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    fun getNotification(context: Context, content: String): Notification {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.YELLOW
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context)
                    .setContentTitle("Scheduled Notification")
                    .setContentText(content)
                    .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
            return builder.build()
        } else {
            builder = Notification.Builder(context)
                    .setContentTitle("Scheduled Notification")
                    .setContentText(content)
                    .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
            return builder.build()
        }
    }
}