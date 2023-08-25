package de.skash.timetrack.feature.service

class WorkTimeTrackService : TimeTrackService() {

    companion object {
        private const val CHANNEL_ID = "Worktime Notifications"
        private const val CHANNEL_NAME = "Worktime Notifications"
        private const val NOTIFICATION_ID = 101
    }

    override fun getNotificationChannelId(): String {
        return CHANNEL_ID
    }

    override fun getNotificationId(): Int {
        return NOTIFICATION_ID
    }

    override fun getNotificationChannelName(): String {
        return CHANNEL_NAME
    }
}