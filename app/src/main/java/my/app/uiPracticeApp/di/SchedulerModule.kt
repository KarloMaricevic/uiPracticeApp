package my.app.uiPracticeApp.di

import dagger.Module
import dagger.Provides
import my.app.uiPracticeApp.utils.scheduler.AppSchedulerProvider
import my.app.uiPracticeApp.utils.scheduler.SchedulerProvider
import javax.inject.Singleton

@Module
interface SchedulerModule {

    companion object {

        @Singleton
        @Provides
        fun provideAppSchedulerProvider(): SchedulerProvider {
            return AppSchedulerProvider
        }
    }
}
