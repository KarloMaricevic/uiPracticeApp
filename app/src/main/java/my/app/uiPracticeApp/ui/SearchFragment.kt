package my.app.uiPracticeApp.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msa.dateedittext.DateEditText
import io.reactivex.disposables.Disposable
import my.app.uiPracticeApp.BaseApplication
import my.app.uiPracticeApp.databinding.FragmentSearchBinding
import my.app.uiPracticeApp.routers.NavigationPlaces
import my.app.uiPracticeApp.routers.SearchNavigationPlaces
import my.app.uiPracticeApp.routers.SearchRouter
import my.app.uiPracticeApp.viewModels.SearchViewModel
import my.app.uiPracticeApp.viewModels.SearchViewModel.FillDateError.FILL_TO_DATE
import my.app.uiPracticeApp.viewModels.SearchViewModel.FillDateError.FILL_FROM_DATE
import my.app.uiPracticeApp.viewModels.SearchViewModel.FillDateError.FILL_TO_AND_FROM_DATE
import my.app.uiPracticeApp.viewModels.SearchViewModel.FillDateError.FILL_DATE
import my.app.uiPracticeApp.viewModels.SearchViewModel.WhatDateIsClicked
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : BaseFragment<SearchViewModel, SearchRouter>() {

    private lateinit var mBinding: FragmentSearchBinding

    private lateinit var mDatePickerDialog: DatePickerDialog

    private val mCalendar = Calendar.getInstance()

    lateinit var mFillErrorObserver: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as BaseApplication)
            .getAppComponent()
            .getSearchSubcomponentFactory()
            .create(this)
            .inject(this)
        mDatePickerDialog = DatePickerDialog(
            context!!,
            { datePicker, _, _, _ ->
                val pickedDate = datePicker.getDate()
                when (mViewModel.whatDateIsClicked.value) {
                    WhatDateIsClicked.DATE -> mBinding.dateEditText.setDate(pickedDate)
                    WhatDateIsClicked.FROM_DATE -> mBinding.fromDateEditText.setDate(pickedDate)
                    WhatDateIsClicked.TO_DATE -> mBinding.toDateEditText.setDate(pickedDate)
                    else -> {
                    }
                }
                mViewModel.whatDateIsClicked.postValue(WhatDateIsClicked.NONE)
            },
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(SearchViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = mViewModel
        mBinding.executePendingBindings()
        startListenForDateEditText()
    }

    private fun startListenForDateEditText() {
        mBinding.dateEditText.listen()
        mBinding.toDateEditText.listen()
        mBinding.fromDateEditText.listen()
    }

    override fun connectViewModel() {
        mViewModel.whatDateIsClicked.observe(viewLifecycleOwner, Observer {
            when (it) {
                WhatDateIsClicked.NONE -> {
                    mDatePickerDialog.hide()
                }
                else -> {
                    mDatePickerDialog.show()
                }
            }
        })
        mViewModel.isExpanded.observe(viewLifecycleOwner, Observer {
            mBinding.moreFiltersTextView.isActivated = it
        })

        mFillErrorObserver = mViewModel.getFillDateError().subscribe(
            {
                when (it) {
                    FILL_DATE -> mBinding.dateTextInputLayout.error = "Fill this filled"
                    FILL_FROM_DATE -> mBinding.fromDateTextInputLayout.error = "Fill this filled"
                    FILL_TO_DATE -> mBinding.toDateTextInputLayout.error = "Fill this filled"
                    FILL_TO_AND_FROM_DATE -> {
                        mBinding.fromDateTextInputLayout.error = "Fill this filled"
                        mBinding.toDateTextInputLayout.error = "Fill this filled"
                    }
                    else -> {
                    }
                }
            },
            {}
        )
    }

    override fun onPause() {
        mFillErrorObserver.dispose()
        super.onPause()
    }

    override fun navigate(navigateTo: NavigationPlaces) {
        super.navigate(navigateTo)
        when (navigateTo) {
            is SearchNavigationPlaces.GoToContainerFragment -> {
                router.navigateToContainerFragment()
            }
            else -> {
            }
        }
    }

    override fun isContainedInsedeOtherFragment(): Boolean = false
}

fun DatePicker.getDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)
    return calendar.time
}

@SuppressLint("SimpleDateFormat")
fun DateEditText.setDate(date: Date) {
    val dateFormat = SimpleDateFormat("""dd/MM/yyyy"""")
    val stringDate = dateFormat.format(date)
    setText(stringDate)
}
