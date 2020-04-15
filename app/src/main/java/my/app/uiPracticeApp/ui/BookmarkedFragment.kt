package my.app.uiPracticeApp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import my.app.uiPracticeApp.BaseApplication
import my.app.uiPracticeApp.core.router.DefaultRouter
import my.app.uiPracticeApp.viewModels.BookmarkedViewModel

class BookmarkedFragment : BaseFragment<BookmarkedViewModel, DefaultRouter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as BaseApplication)
                .getAppComponent()
                .getBookmarkedSubcomponentFactory()
                .create(this)
                .inject(this)
        mViewModel = ViewModelProvider(this,mViewModelFactory).get(BookmarkedViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun connectViewModel() = Unit

    override fun isContainedInsedeOtherFragment(): Boolean = true
}
