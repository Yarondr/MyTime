package me.yarond.mytime.model

enum class Notifications {
    None {
        override val value: String = "None"
    },
    TenMinBefore {
        override val value: String = "10 minutes before"
    },
    HalfHourBefore {
        override val value: String = "30 minutes before"
    },
    OneHourBefore {
        override val value: String = "1 hour before"
    },
    ThreeHoursBefore {
        override val value: String = "3 hours before"
    },
    OneDayBefore {
        override val value: String = "1 day before"
    };

    abstract val value: String
}