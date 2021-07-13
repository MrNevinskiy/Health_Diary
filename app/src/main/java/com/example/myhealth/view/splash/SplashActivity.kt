package com.example.myhealth.view.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.example.myhealth.R
import com.example.myhealth.model.exceptions.NoAuthExceptions
import com.example.myhealth.view.activity.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.android.ext.android.getKoin
import kotlin.coroutines.CoroutineContext

class SplashActivity: AppCompatActivity(), CoroutineScope {

    companion object{
        fun start(context: Context) = Intent(context, SplashActivity::class.java).apply {
            context.startActivity(this)
        }
        private const val RC_SIGN_IN = 123456789
    }

    val model: SplashViewModel by lazy{
        ViewModelProvider(this, getKoin().get()).get(SplashViewModel::class.java)
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    @ObsoleteCoroutinesApi
    override fun onStart() {
        super.onStart()
        dataJob = launch {
            model.getViewState().consumeEach { renderData(it) }
        }
        errorJob = launch {
            model.getErrorChannel().consumeEach { renderError(it) }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    private fun renderError(error: Throwable){
        when(error){
            is NoAuthExceptions -> startLogin()
            else -> error.message?.let{
                showError(it)
            }
        }
    }

    protected fun showError(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    @ObsoleteCoroutinesApi
    override fun onResume() {
        super.onResume()
        model.requestUser()
    }

    fun renderData(data: Boolean) {
        data.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }


    fun startLogin(){
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setTheme(R.style.SplashTheme)
            .setAvailableProviders(providers)
            .build()

        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}