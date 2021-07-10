package com.example.myhealth.model.provider

import com.example.myhealth.model.AppState
import com.example.myhealth.model.NoAuthExceptions
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseProvider(val firebaseAuth: FirebaseAuth, val store: FirebaseFirestore) :
    IFirebaseProvider {

    private val currentUser get() = firebaseAuth.currentUser

    private val healthReference
        get() = currentUser?.let {
            store.collection(USERS_COLLECTION).document(it.uid).collection(HEALTH_COLLECTION)
        } ?: throw NoAuthExceptions()

    override fun subscribeToAllHealthData(): Channel<AppState> =
        Channel<AppState>(Channel.CONFLATED).apply {
            var registration: ListenerRegistration? = null

            try {
                registration = healthReference.addSnapshotListener { snapshot, e ->
                    val value = e?.let {
                        AppState.Error(it)
                    } ?: snapshot?.let {
                        val health =
                            snapshot.documents.mapNotNull { it.toObject(HealthData::class.java) }
                        AppState.Success(health)
                    }

                    value?.let { trySend(it) }
                }
            } catch (t: Throwable) {
                trySend(AppState.Error(t))
            }
            invokeOnClose { registration?.remove() }
        }

    override suspend fun getCurrentUser(): User = suspendCoroutine { continuation ->
        currentUser?.let { User(it.displayName ?: "", it.email ?: "") }?.let {
            continuation.resume(it)
        }
    }

    override suspend fun getAllHealthData(id: String): HealthData? = suspendCoroutine { continuation ->
        try {
            healthReference.document(id).get()
                .addOnSuccessListener { snapshot ->
                    val health = snapshot.toObject(HealthData::class.java)
                    continuation.resume(health)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (t: Throwable) {
            continuation.resumeWithException(t)
        }
    }

    override suspend fun saveHealData(health: HealthData): HealthData = suspendCoroutine { continuation ->
        try {
            healthReference.document(health.date.toString()).set(health)
                .addOnSuccessListener {
                    continuation.resume(health)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (t: Throwable) {
            continuation.resumeWithException(t)
        }
    }

    override suspend fun deleteHealthDate(id: String): Unit = suspendCoroutine { continuation ->
        try {
            healthReference.document(id).delete()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (t: Throwable) {
            continuation.resumeWithException(t)
        }
    }

    companion object {
        private const val HEALTH_COLLECTION = "health"
        private const val USERS_COLLECTION = "users"
    }

}