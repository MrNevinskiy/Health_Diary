package com.example.myhealth.view.activity

import androidx.lifecycle.ViewModel
import com.example.myhealth.view.Screens
import com.example.myhealth.view.base.BaseViewModel
import ru.terrakok.cicerone.Router

class MainViewModel (private val router: Router): ViewModel(){

    fun backPressed() {
        router.exit()
    }

    fun onCreate() {
        router.replaceScreen(Screens.HealthScreen())
    }

}