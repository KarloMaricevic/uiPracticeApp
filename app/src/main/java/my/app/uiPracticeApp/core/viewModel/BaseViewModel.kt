package my.app.uiPracticeApp.core.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import my.app.uiPracticeApp.routers.NavigationPlaces
import my.app.uiPracticeApp.core.viewModel.BaseViewModel.Loading.LOADING
import my.app.uiPracticeApp.core.viewModel.BaseViewModel.Loading.NOT_LOADING

abstract class BaseViewModel : ViewModel() {

    protected val navigateTo = PublishRelay.create<NavigationPlaces>()

    enum class Loading {
        NOT_LOADING,
        LOADING
    }

    private val isLoading = MutableLiveData(NOT_LOADING)

    fun showLoading() {
        isLoading.value = LOADING
    }

    fun showNotLoading() {
        isLoading.value = NOT_LOADING
    }

    fun getIsLoading() = isLoading as LiveData<Loading>

    fun getNavigateTo() = navigateTo as Observable<NavigationPlaces>

    fun navigateBack() {
        navigateTo.accept(NavigationPlaces.NavigateBack)
    }

    fun exitApp() {
        navigateTo.accept(NavigationPlaces.ExitApp)
    }
}
