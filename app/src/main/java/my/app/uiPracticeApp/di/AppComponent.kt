package my.app.uiPracticeApp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import my.app.uiPracticeApp.BaseApplication
import my.app.uiPracticeApp.di.bookmarkedSubcomponent.BookmarkedSubcomponent
import my.app.uiPracticeApp.di.containerSubcomponent.ContainerSubcomponent
import my.app.uiPracticeApp.di.containerSubcomponent.ContainerSubcomponentFactory
import my.app.uiPracticeApp.di.locationSearchSubcomponent.LocationSearchSubcomponent
import my.app.uiPracticeApp.di.locationSearchSubcomponent.LocationSearchSubcomponentFactory
import my.app.uiPracticeApp.di.qualifiers.AppContext
import my.app.uiPracticeApp.di.searchSubcomponent.SearchSubcomponent
import my.app.uiPracticeApp.di.searchSubcomponent.SearchSubcomponentFactory
import my.app.uiPracticeApp.di.settingsSubcomponent.SettingsSubcomponent
import my.app.uiPracticeApp.di.settingsSubcomponent.SettingsSubcomponentFactory
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
        SchedulerModule::class,
        SearchSubcomponentFactory::class,
        LocationSearchSubcomponentFactory::class,
        ContainerSubcomponentFactory::class,
        SettingsSubcomponentFactory::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: BaseApplication)
    fun getSearchSubcomponentFactory() : SearchSubcomponent.Factory
    fun getLocationSearchSubcomponentFactory() : LocationSearchSubcomponent.Factory
    fun getContainerSubcomponentFactory() : ContainerSubcomponent.Factory
    fun getBookmarkedSubcomponentFactory() : BookmarkedSubcomponent.Factory
    fun getSettingsSubcomponentFactory() : SettingsSubcomponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @AppContext appContext: Context): AppComponent
    }
}
