package com.example.myhealth.model.provider

import com.example.myhealth.model.AppState
import com.example.myhealth.model.exceptions.NoAuthExceptions
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseProvider(val firebaseAuth: FirebaseAuth, val store: FirebaseFirestore) :
    IFirebaseProvider {

    private val healthReference
        get() = currentUser?.let { store.collection(USERS_COLLECTION).document(it.uid).collection(HEALTH_COLLECTION) } ?: throw NoAuthExceptions()

    private val currentUser
        get() = firebaseAuth.currentUser

    override fun subscribeToAllHealthData(): ReceiveChannel<AppState> = Channel<AppState>(Channel.CONFLATED).apply {
        try {
            healthReference.addSnapshotListener { snapshot, error ->
                val value = error?.let {
                    AppState.Error(it)
                } ?: snapshot?.let {
                    val healthDataList = it.documents.map { it.toObject(HealthData::class.java) as HealthData }
                    AppState.Success(healthDataList)
                }
                value?.let { offer(it) }
            }
        } catch (e: Throwable){
            offer(AppState.Error(e))
        }
    }

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        val user = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        continuation.resume(user)
    }

    override suspend fun getAllHealthData(id: String): HealthData = suspendCoroutine {continuation ->
        try{
            healthReference.document(id).get()
                .addOnSuccessListener { snapshot->
                    val note = snapshot.toObject(HealthData::class.java) as HealthData
                    continuation.resume(note)
                }.addOnFailureListener{
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable){
            continuation.resumeWithException(e)
        }
    }

    override suspend fun saveHealData(health: HealthData): HealthData = suspendCoroutine { continuation ->
        try {
            healthReference.document(health.id.toString()).set(health)
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