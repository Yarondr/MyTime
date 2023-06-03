package me.yarond.mytime.models

enum class Notifications {
    None {
        override val value: String = "None"
        override val message: String = "right now"
        override val timeArray: ArrayList<Int> = arrayListOf(0, 0)
    },
    TenMinBefore {
        override val value: String = "10 minutes before"
        override val message: String = "in ${value.split(" before")[0]}"
        override val timeArray: ArrayList<Int> = arrayListOf(0, 10)
    },
    HalfHourBefore {
        override val value: String = "30 minutes before"
        override val message: String = "in ${value.split(" before")[0]}"
        override val timeArray: ArrayList<Int> = arrayListOf(0, 30)
    },
    OneHourBefore {
        override val value: String = "1 hour before"
        override val message: String = "in ${value.split(" before")[0]}"
        override val timeArray: ArrayList<Int> = arrayListOf(1, 0)
    },
    ThreeHoursBefore {
        override val value: String = "3 hours before"
        override val message: String = "in ${value.split(" before")[0]}"
        override val timeArray: ArrayList<Int> = arrayListOf(3, 0)
    },
    OneDayBefore {
        override val value: String = "1 day before"
        override val message: String = "in ${value.split(" before")[0]}"
        override val timeArray: ArrayList<Int> = arrayListOf(24, 0)
    };

    abstract val value: String
    abstract val message: String
    abstract val timeArray: ArrayList<Int>
}