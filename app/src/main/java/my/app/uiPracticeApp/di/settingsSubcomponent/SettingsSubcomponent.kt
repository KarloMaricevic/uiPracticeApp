package my.app.uiPracticeApp.di.settingsSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.uiPracticeApp.di.scope.PerFragment
import my.app.uiPracticeApp.ui.SettingsFragment

@Subcomponent
@PerFragment
interface SettingsSubcomponent {
    fun inject(settingsFragment: SettingsFragment)


    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): SettingsSubcomponent
    }
}

