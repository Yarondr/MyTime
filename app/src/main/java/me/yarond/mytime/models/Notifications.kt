package me.yarond.mytime.models

enum class Notifications {
    None {
        override val value: String = "None"
        override val message: String = "right now"
    },
    TenMinBefore {
        override val value: String = "10 minutes before"
        override val message: String = "in ${value.split(" before")[0]}"
    },
    HalfHourBefore {
        override val value: String = "30 minutes before"
        override val message: String = "in ${value.split(" before")[0]}"
    },
    OneHourBefore {
        override val value: String = "1 hour before"
        override val message: String = "in ${value.split(" before")[0]}"
    },
    ThreeHoursBefore {
        override val value: String = "3 hours before"
        override val message: String = "in ${value.split(" before")[0]}"
    },
    OneDayBefore {
        override val value: String = "1 day before"
        override val message: String = "in ${value.split(" before")[0]}"
    };

    abstract val value: String
    abstract val message: String

}