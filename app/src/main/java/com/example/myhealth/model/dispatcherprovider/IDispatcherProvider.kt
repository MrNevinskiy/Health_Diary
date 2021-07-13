package com.example.myhealth.model.dispatcherprovider

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {
    fun io(): CoroutineDispatcher
}