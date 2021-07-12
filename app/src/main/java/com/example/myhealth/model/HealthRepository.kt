package com.example.myhealth.model

import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.provider.IFirebaseProvider
import kotlinx.coroutines.channels.ReceiveChannel

class HealthRepository(private val provider : IFirebaseProvider) {

    fun getHealth(): ReceiveChannel<AppState> = provider.subscribeToAllHealthData()
    suspend fun getCurrentUser() = provider.getCurrentUser()
    suspend fun saveNote(data: HealthData) = provider.saveHealData(data)
    suspend fun getNoteById(id: String) = provider.getAllHealthData(id)
    suspend fun deleteNote(id: String) = provider.deleteHealthDate(id)


}