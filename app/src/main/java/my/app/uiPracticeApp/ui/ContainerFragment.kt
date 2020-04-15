package my.app.uiPracticeApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import my.app.uiPracticeApp.BaseApplication
import my.app.uiPracticeApp.R
import my.app.uiPracticeApp.adapters.BOOKMARKED_LOCATION_PAGE_INDEX
import my.app.uiPracticeApp.adapters.SEARCH_LOCATION_PAGE_INDEX
import my.app.uiPracticeApp.adapters.SETTINGS_PAGE_INDEX
import my.app.uiPracticeApp.adapters.TabLayoutAdapter
import my.app.uiPracticeApp.core.router.DefaultRouter
import my.app.uiPracticeApp.databinding.FragmentContainerBinding
import my.app.uiPracticeApp.viewModels.ContainerViewModel


class ContainerFragment : BaseFragment<ContainerViewModel,DefaultRouter>(){

    private lateinit var mBinding: FragmentContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
                .getAppComponent()
                .getContainerSubcomponentFactory()
                .create(this)
                .inject(this)
        mViewModel = ViewModelProvider(this,mViewModelFactory).get(ContainerViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentContainerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }
    private fun setUpViewPager() {
        mBinding.containerViewPager.isUserInputEnabled = false
        mBinding.containerViewPager.adapter = TabLayoutAdapter(this)
        TabLayoutMediator(mBinding.tabLayout, mBinding.containerViewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTitle(position)
        }.attach()
    }


    private fun getTabIcon(position: Int): Int {
        return when (position) {
            SEARCH_LOCATION_PAGE_INDEX -> R.drawable.selector_tab_search
            BOOKMARKED_LOCATION_PAGE_INDEX -> R.drawable.selector_tab_bookmark
            SETTINGS_PAGE_INDEX -> R.drawable.selector_tab_settings
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTitle(position: Int): String? {
        return when (position) {
            SEARCH_LOCATION_PAGE_INDEX -> context!!.getString(R.string.search)
            BOOKMARKED_LOCATION_PAGE_INDEX -> context!!.getString(R.string.bookmarked)
            SETTINGS_PAGE_INDEX -> context!!.getString(R.string.settings)
            else -> null
        }
    }

    override fun connectViewModel() = Unit

    override fun isContainedInsedeOtherFragment(): Boolean = false
}
