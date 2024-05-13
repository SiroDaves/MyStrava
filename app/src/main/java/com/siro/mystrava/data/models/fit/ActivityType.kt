package com.siro.mystrava.data.models.fit

import com.garmin.fit.Sport

enum class ActivityType(val zeppType: Int, val supported: Boolean, val fitType: Sport, val gaitSupported: Boolean) {

    RUNNING(1, true, Sport.RUNNING, true),
    WALKING(6, true, Sport.WALKING, true),
    CYCLING(9, true, Sport.CYCLING, false),
    INDOOR_SWIMMING(14, true, Sport.SWIMMING, false),
    UNKNOWN(-1, false, Sport.GENERIC, false);

    companion object {
        fun fromZepp(type: Int): ActivityType {
            return ActivityType.entries.find { it.zeppType == type } ?: UNKNOWN
        }
    }

}