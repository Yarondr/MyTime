package me.yarond.mytime.models

class Event(var name: String, var day: Day?, var startTime: String, var endTime: String,
            var notification: Notifications?, var location: String, var notes: String, var once: Boolean): java.io.Serializable {

    var notifed = false

    constructor() : this("", null, "", "", null, "", "", false)

    fun generateId(): String {
        return (this.startTime + this.endTime).replace(":", "")
    }
}