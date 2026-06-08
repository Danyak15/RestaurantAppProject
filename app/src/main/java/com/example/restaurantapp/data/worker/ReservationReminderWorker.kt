package com.example.restaurantapp.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.example.restaurantapp.R

@HiltWorker
class ReservationReminderWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    companion object {
        const val KEY_RESERVATION_ID = "reservation_id"
        const val KEY_GUESTS = "guests"
        const val KEY_DATE_TIME = "date_time"
        const val KEY_MINUTES_BEFORE = "minutes_before"
        const val CHANNEL_ID = "reservation_reminders"
    }

    override suspend fun doWork(): Result {
        val reservationId = inputData.getLong(KEY_RESERVATION_ID, -1)
        val guests = inputData.getInt(KEY_GUESTS, 1)
        val dateTime = inputData.getString(KEY_DATE_TIME) ?: return Result.failure()
        val minutesBefore = inputData.getInt(KEY_MINUTES_BEFORE, 90)

        createNotificationChannel()
        showNotification(reservationId, guests, dateTime, minutesBefore)

        return Result.success()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Напоминание о бронировании",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Уведомления о предстоящих бронированиях"
        }

        val manager = appContext.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun showNotification(
        reservationId: Long,
        guests: Int,
        dateTime: String,
        minutesBefore: Int
    ) {
        val title = if (minutesBefore >= 90) {
            "Напоминание о бронировании"
        } else {
            "Скоро Ваш столик!"
        }

        val formattedTime = try {
            val dt = java.time.LocalDateTime.parse(dateTime)
            dt.format(java.time.format.DateTimeFormatter.ofPattern(
                "d MMMM в HH:mm",
                java.util.Locale("ru")
            ))
        } catch (_: Exception) {
            dateTime
        }

        val text = "Столик на $guests чел. — $formattedTime (через $minutesBefore мин.)"

        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_restaurant_placeholder)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = appContext.getSystemService(NotificationManager::class.java)
        manager.notify(reservationId.toInt() * 10 + (if (minutesBefore >= 90) 0 else 1), notification)
    }
}