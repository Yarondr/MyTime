package me.yarond.mytime.model

class Event(var name: String, var day: Day, var startTime: String, var endTime: String,
            var notification: Notifications, var location: String, var notes: String, var once: Boolean): java.io.Serializable {

}