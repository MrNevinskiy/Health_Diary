package com.example.myhealth.model.dispatcherprovider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider: IDispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}