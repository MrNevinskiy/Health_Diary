package com.example.myhealth.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HealthData(
    val id: Long = 0,
    val date: String? = null,
    val pressureDay: String?= null,
    val pulseDay: String?= null,
    val timeDay: String?= null,
    val timeNight: String?= null,
    val pressureNight: String?= null,
    val pulseNight: String?= null
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false

        // приведение типов:
        other as HealthData

        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
