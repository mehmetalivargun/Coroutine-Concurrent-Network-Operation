package com.mehmetalivargun.concurrentcoroutineexample

import android.app.Application
import com.mehmetalivargun.concurrentcoroutineexample.api.generateApi

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        generateApi(this)
    }
}