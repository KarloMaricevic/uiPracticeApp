package my.app.uiPracticeApp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.Disposable
import my.app.uiPracticeApp.routers.NavigationPlaces
import my.app.uiPracticeApp.core.router.Router
import my.app.uiPracticeApp.core.viewModel.BaseViewModel
import my.app.uiPracticeApp.core.viewModel.BaseViewModel.Loading.LOADING
import my.app.uiPracticeApp.core.viewModel.BaseViewModel.Loading.NOT_LOADING
import my.app.uiPracticeApp.core.views.LoadingView
import my.app.uiPracticeApp.core.views.NavigationView
import my.app.uiPracticeApp.utils.widgets.CustomProgressDialog
import java.lang.Exception
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, R : Router> : Fragment(),
    LoadingView, NavigationView {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var router: R

    protected lateinit var mViewModel: VM

    private var mLadingDialog = CustomProgressDialog()

    // because lifecycle's onPause called before onCreateView
    private var mNavigationDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!isContainedInsedeOtherFragment()) {
            requireActivity()
                .onBackPressedDispatcher
                .addCallback(this) {
                    mViewModel.navigateBack()
                }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoading()
        if (!isContainedInsedeOtherFragment()) {
            observeNavigation()
        }
        connectViewModel()
    }

    override fun onPause() {
        mNavigationDisposable?.dispose()
        super.onPause()
    }

    private fun observeLoading() {
        mViewModel.getIsLoading().observe(viewLifecycleOwner, Observer {
            when (it) {
                NOT_LOADING -> hideLoading()
                LOADING -> showLoading()
                else -> {
                }
            }
        })
    }

    override fun showLoading() {
        if (!mLadingDialog.isVisible) {
            mLadingDialog.show(activity!!.supportFragmentManager, "Loading")
        }
    }

    override fun hideLoading() {
        if (mLadingDialog.isVisible) {
            mLadingDialog.dismiss()
        }
    }

    override fun navigate(navigateTo: NavigationPlaces) {
        when (navigateTo) {
            is NavigationPlaces.NavigateBack -> router.navigateBack()
            is NavigationPlaces.ExitApp -> router.exitApp()
        }
    }

    private fun observeNavigation() {
        mNavigationDisposable = mViewModel.getNavigateTo().subscribe(
            {
                navigate(it)
            },
            {
                throw Exception("Navigation error: " + it.message)
            })
    }

    abstract fun connectViewModel()

    abstract fun isContainedInsedeOtherFragment(): Boolean
}
