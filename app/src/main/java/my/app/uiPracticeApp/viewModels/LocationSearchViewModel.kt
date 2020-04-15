package my.app.uiPracticeApp.viewModels

import androidx.lifecycle.MutableLiveData
import my.app.uiPracticeApp.core.viewModel.BaseViewModel
import my.app.uiPracticeApp.data.entities.City
import my.app.uiPracticeApp.data.entities.Location
import javax.inject.Inject

class LocationSearchViewModel @Inject constructor() : BaseViewModel() {
    val mLocationList = MutableLiveData(locationList)

    val mSearchParametersList = MutableLiveData(searchParamsList)

    fun deleteLocation(index: Int) {
        val oldList = mLocationList.value
        oldList?.removeAt(index)
        mLocationList.value = oldList
    }

    companion object {
        val locationList = arrayListOf(
            Location(
                1, 6, 525, City("Paris", "France"), false
            ), Location(
                2, 2, 257, City("Tokyo", "Japan"), true
            ), Location(
                3, 3, 232, City("Washington", "USA"), false
            ), Location(
                4, 12, 643, City("Argentina", "Argentina"), false
            )
        )

        val searchParamsList = arrayListOf(
            "24.4.1997",
            "29.4.1998",
            "600$"
        )
    }
}
