package my.app.sportvideofeedapp.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import my.app.uiPracticeApp.core.router.Router
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_FORBIDDEN
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_UNAUTHORIZED
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_INTERNAL_ERROR
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_BAD_REQUEST
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.JSON_SYNTAX_EXCEPTION
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.UNKNOWN
import my.app.uiPracticeApp.core.views.NetworkView
import my.app.uiPracticeApp.ui.BaseFragment
import my.app.uiPracticeApp.utils.widgets.GeneralErrorSnackBarBuilder

abstract class NetworkFragment<VM : NetworkViewModel, R : Router> :
    BaseFragment<VM, R>(), NetworkView {

    private val mGeneralErrorSnackBarBuilder = GeneralErrorSnackBarBuilder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetworkErrors()
    }

    abstract fun getRootView(): View

    private fun observeNetworkErrors() {
        mViewModel.getNetworkError().observe(viewLifecycleOwner, Observer {
            when (it) {
                HTTP_UNAUTHORIZED -> handleHTTPUnauthorized()
                HTTP_FORBIDDEN -> handleHTTPForbidden()
                HTTP_INTERNAL_ERROR -> handleInternalError()
                HTTP_BAD_REQUEST -> handleHTTPBadRequest()
                JSON_SYNTAX_EXCEPTION -> handleJsonSyntaxException()
                UNKNOWN -> handleOtherError()
                else -> { }
            }
        })
    }

    override fun handleHTTPUnauthorized() {
        mGeneralErrorSnackBarBuilder.showErrorSnackBar(getRootView())
    }

    override fun handleHTTPForbidden() {
        mGeneralErrorSnackBarBuilder.showErrorSnackBar(getRootView())
    }

    override fun handleInternalError() {
        mGeneralErrorSnackBarBuilder.showErrorSnackBar(getRootView())
    }

    override fun handleHTTPBadRequest() {
        mGeneralErrorSnackBarBuilder.showErrorSnackBar(getRootView())
    }

    override fun handleJsonSyntaxException() {
        mGeneralErrorSnackBarBuilder.showErrorSnackBar(getRootView())
    }

    override fun handleOtherError() {
        mGeneralErrorSnackBarBuilder.showErrorSnackBar(getRootView())
    }
}
