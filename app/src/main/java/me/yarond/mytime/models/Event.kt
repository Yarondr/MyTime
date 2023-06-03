package me.yarond.mytime.models

class Event(var name: String, var day: Day?, var startTime: String, var endTime: String,
            var notification: Notifications?, var location: String, var notes: String, var once: Boolean): java.io.Serializable {

    var notified = false
    var id = ""

    constructor() : this("", null, "", "", null, "", "", false)

    fun generateId(): String {
        if (id != "") return id

        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

        id = (1..10)
            .map { allowedChars.random() }
            .joinToString("")
        return id
    }
}