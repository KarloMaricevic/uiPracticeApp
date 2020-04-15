package my.app.uiPracticeApp.di.containerSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.uiPracticeApp.di.scope.PerFragment
import my.app.uiPracticeApp.ui.ContainerFragment

@Subcomponent
@PerFragment
interface ContainerSubcomponent {
    fun inject(containerFragment: ContainerFragment)


    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance fragment: Fragment) : ContainerSubcomponent
    }
}
