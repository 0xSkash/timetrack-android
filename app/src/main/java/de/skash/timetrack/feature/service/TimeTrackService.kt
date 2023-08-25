package de.skash.timetrack.feature.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import de.skash.timetrack.R
import de.skash.timetrack.core.error.MissingTimerActionException
import java.util.Timer
import java.util.TimerTask

abstract class TimeTrackService : Service() {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NotificationManager::class.java)
    }

    private var timeElapsed: Int = 0
    private var isTimerRunning = false

    private var timer = Timer()
    private var isInForegroundMode = false

    companion object {
        // Actions
        const val START_TIMER = "action_start_timer"
        const val STOP_TIMER = "action_stop_timer"
        const val GET_TIMER_STATUS = "action_get_timer_status"
        const val MOVE_TIMER_TO_FOREGROUND = "action_move_to_foreground"
        const val MOVE_TIMER_TO_BACKGROUND = "action_move_to_background"

        // Intent extras
        const val TIMER_ACTION = "timer_action"
        const val IS_TIMER_RUNNING = "timer_action"
        const val TIME_ELAPSED = "time_elapsed"

        // Intent Actions
        const val TIMER_TICK = "timer_tick"
        const val TIMER_STATUS = "timer_status"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val action = intent?.getStringExtra(TIMER_ACTION)
            ?: throw MissingTimerActionException("you may not start the service without specifying an action")

        if (!doesNotificationChannelExist()) {
            createNotificationChannel()
        }

        when (action) {
            START_TIMER -> startTimer()
            STOP_TIMER -> stopTimer()
            GET_TIMER_STATUS -> postStatus()
            MOVE_TIMER_TO_FOREGROUND -> moveTimerToForeground()
            MOVE_TIMER_TO_BACKGROUND -> moveTimerToBackground()
        }
        return START_STICKY
    }

    private fun startTimer() {
        isTimerRunning = true
        timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                timeElapsed++

                if (isInForegroundMode) {
                    notificationManager.notify(
                        getNotificationId(),
                        createNotification()
                    )
                }
            }
        }, 0, 1000)
    }

    private fun stopTimer() {
        timer.cancel()
        isTimerRunning = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun postStatus() {

    }

    private fun moveTimerToForeground() {
        isInForegroundMode = true
        startForeground(getNotificationId(), createNotification())
    }

    private fun moveTimerToBackground() {
        isInForegroundMode = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    abstract fun getNotificationChannelId(): String
    abstract fun getNotificationId(): Int
    abstract fun getNotificationChannelName(): String

    private fun doesNotificationChannelExist(): Boolean {
        return notificationManager.getNotificationChannel(getNotificationChannelId()) != null
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            getNotificationChannelId(),
            getNotificationChannelName(),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setSound(null, null)
            setShowBadge(true)
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val stopwatchService = Intent(this, TimeTrackService::class.java)
        stopwatchService.putExtra(TIMER_ACTION, STOP_TIMER)
        val stopPendingIntent =
            PendingIntent.getService(this, 0, stopwatchService, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, getNotificationChannelId())
            .setContentTitle("Tracking")
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setOngoing(true)
            .setSmallIcon(R.drawable.icn_timer)
            .addAction(R.drawable.icn_timer, "Stop", stopPendingIntent)
            .setContentText(formatElapsedTime())
            .build()
    }

    private fun formatElapsedTime(): String {
        val hours: Int = (timeElapsed / 60) / 60
        val minutes: Int = timeElapsed / 60
        val seconds: Int = timeElapsed % 60

        return "${"%02d".format(hours)}:${"%02d".format(minutes)}:${
            "%02d".format(
                seconds
            )
        }"
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}