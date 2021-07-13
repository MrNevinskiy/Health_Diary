package com.example.myhealth.model.repo

import com.example.myhealth.model.AppState
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.data.User
import kotlinx.coroutines.channels.ReceiveChannel

interface IHealthRepository<T> {
    fun getHealthData(): ReceiveChannel<AppState>
    suspend fun saveHealthData(healthData: HealthData): T
    suspend fun getHealthDataById(id: String): T
    suspend fun getCurrentUser(): User?
    suspend fun deleteHealthData(id: String): Unit
}