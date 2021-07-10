package com.example.myhealth.model.provider

import com.example.myhealth.model.AppState
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.data.User
import kotlinx.coroutines.channels.ReceiveChannel

interface IFirebaseProvider {
    fun subscribeToAllHealthData(): ReceiveChannel<AppState>

    suspend fun getCurrentUser(): User
    suspend fun getAllHealthData(id: String): HealthData?
    suspend fun saveHealData(health: HealthData): HealthData
    suspend fun deleteHealthDate(id: String)
}