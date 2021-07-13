package com.example.myhealth.view

import ru.terrakok.cicerone.android.support.SupportAppScreen

//import com.github.terrakok.cicerone.androidx.FragmentScreen
//
//object Screens {
//    fun health() = FragmentScreen { HealthListFragment() }
//    fun data() = FragmentScreen { EnterDataFragment() }
//}

class Screens {
    class HealthScreen() : SupportAppScreen() {
        override fun getFragment() = HealthListFragment.newInstance()
    }
}