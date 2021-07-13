package com.example.myhealth.model.repo

import com.example.myhealth.model.AppState
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.data.User
import com.example.myhealth.model.provider.IFirebaseProvider
import kotlinx.coroutines.channels.ReceiveChannel

class HealthRepository(val dataProvider: IFirebaseProvider): IHealthRepository<HealthData> {
    override fun getHealthData(): ReceiveChannel<AppState> =
        dataProvider.subscribeToAllHealthData()

    override suspend fun saveHealthData(healthData: HealthData) =
        dataProvider.saveHealData(healthData)

    override suspend fun getHealthDataById(id: String): HealthData =
        dataProvider.getAllHealthData(id)

    override suspend fun getCurrentUser(): User? = dataProvider.getCurrentUser()

    override suspend fun deleteHealthData(id: String) = dataProvider.deleteHealthDate(id)
}