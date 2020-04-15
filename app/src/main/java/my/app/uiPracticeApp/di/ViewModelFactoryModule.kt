package my.app.uiPracticeApp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.uiPracticeApp.di.key.ViewModelKey
import my.app.uiPracticeApp.viewModels.*

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun provideSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationSearchViewModel::class)
    fun provideLocationSearchViewModel(locationSearchViewModel: LocationSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContainerViewModel::class)
    fun provideContainerViewModel(containerViewModel: ContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkedViewModel::class)
    fun provideBookmarkedViewModel(bookmarkedViewModel: BookmarkedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun provideSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
}
