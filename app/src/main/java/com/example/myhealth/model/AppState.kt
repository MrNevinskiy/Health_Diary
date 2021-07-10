package com.example.myhealth.model

sealed class AppState {
    data class Success<out T>(val data: T): AppState()
    data class Error(val error: Throwable): AppState()
}