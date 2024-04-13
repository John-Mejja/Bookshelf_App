package com.example.bookshelfapp

import android.app.Application
import com.example.bookshelfapp.data.di.AppContainer
import com.example.bookshelfapp.data.di.DefaultAppContainer

class BookshelfApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}