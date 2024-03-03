package ru.plumsoftware.coffeeapp.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Parcel
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.plumsoftware.coffeeapp.ui.screens.main.MainActivity
import ru.plumsoftware.coffeeapp.R

class FirebaseMessagingService() : FirebaseMessagingService(), Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun onNewToken(token: String) {
//        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        if (remoteMessage.data.isNotEmpty()) {
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//
//            val pendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//            val defaultSoundUri =
//                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//            val notificationBuilder = NotificationCompat.Builder(
//                this,
//                "ru.plumsoftware.coffeeapp.FirebaseMessagingService"
//            )
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(remoteMessage.notification?.title)
//                .setContentText(if (remoteMessage.notification?.body != null) remoteMessage.notification?.body else "")
//                .setAutoCancel(true)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//
//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.notify(0, notificationBuilder.build())
//        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FirebaseMessagingService> {
        override fun createFromParcel(parcel: Parcel): FirebaseMessagingService {
            return FirebaseMessagingService(parcel)
        }

        override fun newArray(size: Int): Array<FirebaseMessagingService?> {
            return arrayOfNulls(size)
        }
    }
}