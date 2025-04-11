package com.siro.mystrava.domain.entities

sealed class UiState {
    object Loading : UiState()
    class Loaded(val calendarActivities: CalendarActivities) : UiState()
    class Error(val errorMessage: String) : UiState()
}