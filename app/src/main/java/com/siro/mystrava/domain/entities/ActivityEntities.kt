package com.siro.mystrava.domain.entities

enum class ActivityType { Run, Swim, Bike, All }
enum class UnitType { Imperial, Metric }
enum class MeasureType { Absolute, Relative }

sealed class UiState {
    object Loading : UiState()
    class Loaded(val calendarActivities: CalendarActivities) : UiState()
    class Error(val errorMessage: String) : UiState()
}