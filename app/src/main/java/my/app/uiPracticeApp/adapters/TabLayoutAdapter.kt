package my.app.uiPracticeApp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.app.uiPracticeApp.ui.BookmarkedFragment
import my.app.uiPracticeApp.ui.LocationSearchFragment
import my.app.uiPracticeApp.ui.SettingsFragment

const val SEARCH_LOCATION_PAGE_INDEX = 0
const val BOOKMARKED_LOCATION_PAGE_INDEX = 1
const val SETTINGS_PAGE_INDEX = 2

class TabLayoutAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreator : Map<Int,() ->  Fragment> = mapOf(
        SEARCH_LOCATION_PAGE_INDEX to { LocationSearchFragment() },
        BOOKMARKED_LOCATION_PAGE_INDEX to { BookmarkedFragment() },
        SETTINGS_PAGE_INDEX to { SettingsFragment() })

    override fun getItemCount(): Int  = tabFragmentCreator.count()

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
