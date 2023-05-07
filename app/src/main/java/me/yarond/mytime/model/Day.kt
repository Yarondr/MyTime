package me.yarond.mytime.model

import me.yarond.mytime.R

enum class Day {
    Sunday {
        override val shortName: String = R.string.sunday_short.toString()
        override val value: String = R.string.sunday.toString()
    },
    Monday {
        override val shortName: String = R.string.monday_short.toString()
        override val value: String = R.string.monday.toString()
    },
    Tuesday {
        override val shortName: String = R.string.tuesday_short.toString()
        override val value: String = R.string.tuesday.toString()
    },
    Wednesday {
        override val shortName: String = R.string.wednesday_short.toString()
        override val value: String = R.string.wednesday.toString()
    },
    Thursday {
        override val shortName: String = R.string.thursday_short.toString()
        override val value: String = R.string.thursday.toString()
    },
    Friday {
        override val shortName: String = R.string.friday_short.toString()
        override val value: String = R.string.friday.toString()
    },
    Saturday {
        override val shortName: String = R.string.saturday_short.toString()
        override val value: String = R.string.saturday.toString()
    };

    abstract val shortName: String
    abstract val value: String
}