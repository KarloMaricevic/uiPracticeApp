package my.app.uiPracticeApp.di.locationSearchSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.uiPracticeApp.ui.LocationSearchFragment

@Subcomponent
interface LocationSearchSubcomponent {

    fun inject(locationSearchFragment: LocationSearchFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance fragment : Fragment) : LocationSearchSubcomponent
    }
}
