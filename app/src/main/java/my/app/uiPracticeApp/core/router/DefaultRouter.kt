package my.app.uiPracticeApp.core.router

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

open class DefaultRouter @Inject constructor(protected val mFragment: Fragment) : Router {

    override fun navigateBack() {
        if (!mFragment.findNavController().navigateUp()) {
            exitApp()
        }
    }

    override fun exitApp() {
        mFragment.activity!!.finishAndRemoveTask()
    }
}
