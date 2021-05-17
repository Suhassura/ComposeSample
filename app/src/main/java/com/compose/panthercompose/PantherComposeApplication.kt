package com.compose.panthercompose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PantherComposeApplication : Application() {

    init {
        instance = requireNotNull(this)
    }

    companion object {
        private lateinit var instance: PantherComposeApplication

        fun applicationContext(): Context {
            return instance
        }
    }

}