package my.app.uiPracticeApp.viewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import my.app.uiPracticeApp.core.viewModel.BaseViewModel
import my.app.uiPracticeApp.routers.SearchNavigationPlaces.GoToContainerFragment
import java.util.regex.Pattern
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val datePattern = Pattern.compile(regExDatePattern)


    enum class FillDateError {
        FILL_DATE,
        FILL_TO_DATE,
        FILL_FROM_DATE,
        FILL_TO_AND_FROM_DATE
    }

    enum class WhatDateIsClicked {
        NONE,
        DATE,
        FROM_DATE,
        TO_DATE
    }

    val cityName = MutableLiveData<String>()
    val countyName = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val fromDate = MutableLiveData<String>()
    val toDate = MutableLiveData<String>()
    val isExpanded = MutableLiveData(false)
    var whatDateIsClicked = MutableLiveData(WhatDateIsClicked.NONE)


    private val isFromAndToDateNotEmpty = MediatorLiveData<Boolean>().apply {
        addSource(fromDate) {
            value = !it.isNullOrEmpty() && !toDate.value.isNullOrEmpty()

        }
        addSource(toDate) {
            value = !it.isNullOrEmpty() && !fromDate.value.isNullOrEmpty()
        }
    }
    val showDate = MediatorLiveData<Boolean>().apply {
        addSource(isFromAndToDateNotEmpty) {
            value = !(it && isExpanded.value == true)
        }
        addSource(isExpanded) {
            value = !(it && isFromAndToDateNotEmpty.value == true)
        }
    }
    private val mFillDateError = PublishRelay.create<FillDateError>()

    fun clickMoreFilters() {
        isExpanded.value = isExpanded.value?.not()
    }

    fun clickDateImage() {
        whatDateIsClicked.postValue(WhatDateIsClicked.DATE)
    }

    fun clickFromDateImage() {
        whatDateIsClicked.postValue(WhatDateIsClicked.FROM_DATE)
    }

    fun clickToDateImage() {
        whatDateIsClicked.postValue(WhatDateIsClicked.TO_DATE)
    }


    fun searchButtonClicked() {
        if (checkSearchParameters()) {
            navigateTo.accept(GoToContainerFragment)
        }
    }

    @Suppress("ReturnCount")
    private fun checkSearchParameters(): Boolean {
        if (showDate.value == true) {
            if (!datePattern.matcher(date.value ?: "").matches()) {
                mFillDateError.accept(FillDateError.FILL_DATE)
                return false
            }
        } else {
                val fromDateFilledCorrectly = datePattern.matcher(fromDate.value ?: "").matches()
                val toDateFilledCorrectly = datePattern.matcher(toDate.value ?: "").matches()
                if (!fromDateFilledCorrectly && !toDateFilledCorrectly) {
                    mFillDateError.accept(FillDateError.FILL_TO_AND_FROM_DATE)
                    return false
                } else if (!fromDateFilledCorrectly) {
                    mFillDateError.accept(FillDateError.FILL_FROM_DATE)
                    return false
                } else if (!toDateFilledCorrectly) {
                    mFillDateError.accept(FillDateError.FILL_TO_DATE)
                    return false
                }
        }
        return true
    }

    fun getFillDateError() = mFillDateError as Observable<FillDateError>

    companion object{
        const val regExDatePattern = """\d{2}/\d{2}/\d{4}"""
    }
}
