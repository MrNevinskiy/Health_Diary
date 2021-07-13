package com.example.myhealth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.model.dispatcherprovider.DispatcherProvider
import com.example.myhealth.model.provider.FirebaseProvider
import com.example.myhealth.model.provider.IFirebaseProvider
import com.example.myhealth.model.repo.HealthRepository
import com.example.myhealth.model.repo.IHealthRepository
import com.example.myhealth.view.HealthListViewModel
import com.example.myhealth.view.activity.MainViewModel
import com.example.myhealth.view.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Provider

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseProvider(get(), get()) } bind IFirebaseProvider::class
    single <IHealthRepository<HealthData>>{ HealthRepository(get()) }
    single { DispatcherProvider() }
}

val viewModelModule = module {
    single {
        var map =
            mutableMapOf(
                SplashViewModel::class.java to Provider<ViewModel>{SplashViewModel(get())},
                MainViewModel::class.java to Provider<ViewModel>{ MainViewModel(get()) },
                HealthListViewModel::class.java to Provider<ViewModel>{HealthListViewModel(get<IHealthRepository<HealthData>>() as HealthRepository) },
            )
        map
    }
    single<ViewModelProvider.Factory> { ViewModelFactory(get())}
}

val navigation = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    factory { cicerone.navigatorHolder }
    factory { cicerone.router }
}

val splashActivity = module {
    factory { SplashViewModel(get()) }
}

val mainActivity = module {
    factory { MainViewModel(get()) }
}

val healthFragmentModule = module {
    factory { HealthListViewModel(get<IHealthRepository<HealthData>>() as HealthRepository) }
}

