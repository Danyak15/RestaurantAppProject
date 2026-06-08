package com.example.restaurantapp.data.worker

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationReminderScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun schedule(
        reservationId: Long,
        dateTimeString: String,
        guests: Int
    ) {
        val dateTime = try {
            LocalDateTime.parse(dateTimeString)
        } catch (_: Exception) {
            return
        }

        val now = LocalDateTime.now()

        scheduleReminder(
            reservationId = reservationId,
            dateTime = dateTime,
            now = now,
            guests = guests,
            minutesBefore = 90
        )

        scheduleReminder(
            reservationId = reservationId,
            dateTime = dateTime,
            now = now,
            guests = guests,
            minutesBefore = 15
        )
    }

    private fun scheduleReminder(
        reservationId: Long,
        dateTime: LocalDateTime,
        now: LocalDateTime,
        guests: Int,
        minutesBefore: Int
    ) {
        val triggerTime = dateTime.minusMinutes(minutesBefore.toLong())

        if (!dateTime.isAfter(now)) return

        val delayMillis = if (triggerTime.isAfter(now)) {
            java.time.Duration.between(now, triggerTime).toMillis()
        } else {
            0L
        }

        val inputData = Data.Builder()
            .putLong(ReservationReminderWorker.KEY_RESERVATION_ID, reservationId)
            .putInt(ReservationReminderWorker.KEY_GUESTS, guests)
            .putString(ReservationReminderWorker.KEY_DATE_TIME, dateTime.toString())
            .putInt(ReservationReminderWorker.KEY_MINUTES_BEFORE, minutesBefore)
            .build()

        val request = OneTimeWorkRequestBuilder<ReservationReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "reservation_reminder_${reservationId}_${minutesBefore}min",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    fun cancel(reservationId: Long) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork("reservation_reminder_${reservationId}_90min")
        workManager.cancelUniqueWork("reservation_reminder_${reservationId}_15min")
    }
}