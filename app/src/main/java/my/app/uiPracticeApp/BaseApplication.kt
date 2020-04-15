package my.app.uiPracticeApp

import android.app.Application
import my.app.uiPracticeApp.di.AppComponent
import my.app.uiPracticeApp.di.DaggerAppComponent

class BaseApplication : Application() {
    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.factory().create(this)
    }

    fun getAppComponent() = mAppComponent
}
