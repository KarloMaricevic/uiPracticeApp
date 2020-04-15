package my.app.uiPracticeApp.core.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_BAD_REQUEST
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_FORBIDDEN
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_INTERNAL_ERROR
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.HTTP_UNAUTHORIZED
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.JSON_SYNTAX_EXCEPTION
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.NONE
import my.app.uiPracticeApp.core.viewModel.NetworkViewModel.NetworkError.UNKNOWN
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

abstract class NetworkViewModel : RxViewModel() {

    enum class NetworkError {
        NONE,
        HTTP_UNAUTHORIZED,
        HTTP_FORBIDDEN,
        HTTP_INTERNAL_ERROR,
        HTTP_BAD_REQUEST,
        JSON_SYNTAX_EXCEPTION,
        UNKNOWN
    }

    private val networkError = MutableLiveData<NetworkError>(NONE)

    protected fun handleNetworkError(e: Throwable) {
        when (e) {
            is HttpException -> {
                when (e.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> handleHTTPUnauthorized()
                    HttpsURLConnection.HTTP_FORBIDDEN -> handleHTTPForbidden()
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> handleInternalError()
                    HttpsURLConnection.HTTP_BAD_REQUEST -> handleHTTPBadRequest()
                    else -> handleOtherError()
                }
            }
            is JsonSyntaxException -> {
                handleJsonSyntaxException()
            }
            else -> {
                handleOtherError()
            }
        }
    }

    protected fun handleHTTPUnauthorized() {
        networkError.value = HTTP_UNAUTHORIZED
    }

    protected fun handleHTTPForbidden() {
        networkError.value = HTTP_FORBIDDEN
    }

    protected fun handleInternalError() {
        networkError.value = HTTP_INTERNAL_ERROR
    }

    protected fun handleHTTPBadRequest() {
        networkError.value = HTTP_BAD_REQUEST
    }

    protected fun handleJsonSyntaxException() {
        networkError.value = JSON_SYNTAX_EXCEPTION
    }

    protected fun handleOtherError() {
        networkError.value = UNKNOWN
    }

    fun getNetworkError() = networkError as LiveData<NetworkError>
}
