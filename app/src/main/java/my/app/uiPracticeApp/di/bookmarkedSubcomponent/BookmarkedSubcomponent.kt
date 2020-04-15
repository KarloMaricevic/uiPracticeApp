package my.app.uiPracticeApp.di.bookmarkedSubcomponent

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import my.app.uiPracticeApp.di.scope.PerFragment
import my.app.uiPracticeApp.ui.BookmarkedFragment

@Subcomponent
@PerFragment
interface BookmarkedSubcomponent {
    fun inject(bookmarkedFragment: BookmarkedFragment)


    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance fragment: Fragment) : BookmarkedSubcomponent
    }
}
