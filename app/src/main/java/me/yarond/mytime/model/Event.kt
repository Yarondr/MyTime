package me.yarond.mytime.model

class Event(var name: String, var day: Day, var startTime: String, var endTime: String,
            var notification: Notifications, var location: String, var notes: String, var once: Boolean): java.io.Serializable {

    constructor() : this("", Day.Sunday, "", "", Notifications.None, "", "", false)

    fun generateId(): String {
        return this.startTime.replace(":", "") + this.endTime.replace(":", "")
    }
}