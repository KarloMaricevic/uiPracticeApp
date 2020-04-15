package my.app.uiPracticeApp.di.searchSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.uiPracticeApp.di.scope.PerFragment
import my.app.uiPracticeApp.ui.SearchFragment

@Subcomponent
@PerFragment
interface SearchSubcomponent {
    fun inject(searchFragment: SearchFragment)


    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance fragment: Fragment) : SearchSubcomponent
    }
}
