package my.app.uiPracticeApp.routers

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.app.uiPracticeApp.core.router.DefaultRouter
import my.app.uiPracticeApp.ui.SearchFragmentDirections
import javax.inject.Inject

class SearchRouter @Inject constructor(fragment: Fragment) : DefaultRouter(fragment) {

    fun navigateToContainerFragment() {
        val action = SearchFragmentDirections.actionSearchFragmentToContainerFragment()
        mFragment.findNavController().navigate(action)
    }
}
