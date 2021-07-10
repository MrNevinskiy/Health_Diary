package com.example.myhealth.di

import com.example.myhealth.model.HealthRepository
import com.example.myhealth.model.provider.FirebaseProvider
import com.example.myhealth.model.provider.IFirebaseProvider
import com.example.myhealth.view.EnterDataViewModel
import com.example.myhealth.view.HealthListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<IFirebaseProvider> { FirebaseProvider(get(), get()) }
    single { HealthRepository(get()) }
}

val healthFragmentModule = module {
    viewModel { HealthListViewModel(get()) }
}

val enterDataFragmentModule = module {
    viewModel { EnterDataViewModel(get()) }
}

