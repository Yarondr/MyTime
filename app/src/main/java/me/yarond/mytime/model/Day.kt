package me.yarond.mytime.model

import me.yarond.mytime.R

enum class Day {
    Sunday {
        override val shortName: String = "SUN"
        override val value: String = "Sunday"
    },
    Monday {
        override val shortName: String = "MON"
        override val value: String = "Monday"
    },
    Tuesday {
        override val shortName: String = "TUE"
        override val value: String = "Tuesday"
    },
    Wednesday {
        override val shortName: String = "WED"
        override val value: String = "Wednesday"
    },
    Thursday {
        override val shortName: String = "THU"
        override val value: String = "Thursday"
    },
    Friday {
        override val shortName: String = "FRI"
        override val value: String = "Friday"
    },
    Saturday {
        override val shortName: String = "SAT"
        override val value: String = "Saturday"
    };

    abstract val shortName: String
    abstract val value: String
}