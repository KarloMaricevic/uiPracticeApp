package my.app.uiPracticeApp.ui

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import my.app.uiPracticeApp.BaseApplication
import my.app.uiPracticeApp.R
import my.app.uiPracticeApp.adapters.DeleteItemInterface
import my.app.uiPracticeApp.adapters.locationAdapter.LocationAdapter
import my.app.uiPracticeApp.adapters.locationAdapter.LocationAdapterItemDecorator
import my.app.uiPracticeApp.adapters.searchParametars.SearchParametersAdapter
import my.app.uiPracticeApp.adapters.searchParametars.SearchParametersItemDecorator
import my.app.uiPracticeApp.core.router.DefaultRouter
import my.app.uiPracticeApp.databinding.FragmentLocationSearchBinding
import my.app.uiPracticeApp.utils.recyclerViewUtils.CardPageLinarManger
import my.app.uiPracticeApp.utils.recyclerViewUtils.snapHelper.OnSnapPositionChangeListener
import my.app.uiPracticeApp.utils.recyclerViewUtils.snapHelper.SnapOnScrollListener
import my.app.uiPracticeApp.utils.recyclerViewUtils.touchHelper.OnSwipeUpDeleteSimpleCallback
import my.app.uiPracticeApp.viewModels.LocationSearchViewModel

@Suppress("TooManyFunctions")
class LocationSearchFragment : BaseFragment<LocationSearchViewModel, DefaultRouter>(),
        OnSnapPositionChangeListener,
        DeleteItemInterface {

    private lateinit var mBinding: FragmentLocationSearchBinding

    private lateinit var mLocationAdapter: LocationAdapter

    private val mSearchParametersAdapter =
            SearchParametersAdapter()

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private val mItemDecorator: RecyclerView.ItemDecoration =
            LocationAdapterItemDecorator()

    private lateinit var mItemTouchHelper: ItemTouchHelper.SimpleCallback

    private lateinit var mItemTouch: ItemTouchHelper

    private val mSnapHelper: SnapHelper = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
                .getAppComponent()
                .getLocationSearchSubcomponentFactory()
                .create(this)
                .inject(this)
        mViewModel =
                ViewModelProvider(this, mViewModelFactory).get(LocationSearchViewModel::class.java)
        mLocationAdapter =
                LocationAdapter(
                        context!!,
                        activity!!
                )
        mLayoutManager =
                CardPageLinarManger(
                        context!!,
                        RecyclerView.HORIZONTAL
                )
        mItemTouchHelper = OnSwipeUpDeleteSimpleCallback(this)
        mItemTouch = ItemTouchHelper(mItemTouchHelper)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLocationSearchBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchParametersRecyclerView()
        setUpLocationAdapter()
        setUpLocationListRecyclerView()
    }

    override fun onSnapPositionChange(position: Int) {
        AnimatorInflater.loadAnimator(context, R.animator.animator_item_swiped).apply {
            mBinding.recyclerAndPositionCounter.positionCounter.itemPositionTextView.apply {
                setUpViewPivotForAnimation(this)
                text = (position + 1).toString()
                setTarget(this)
            }
            start()
        }
    }

    override fun connectViewModel() {
        mViewModel.mLocationList.observe(
                viewLifecycleOwner,
                Observer { mLocationAdapter.setData(it) }
        )

        mViewModel.mSearchParametersList.observe(
                viewLifecycleOwner,
                Observer { mSearchParametersAdapter.setData(it) }
        )
    }

    override fun isContainedInsedeOtherFragment(): Boolean = true

    override fun deleteItem(position: Int) {
        mViewModel.deleteLocation(position)
    }


    private fun setSearchParametersRecyclerView() {
        mBinding.searchParametersRecyclerView.addItemDecoration(SearchParametersItemDecorator(searchParamsMargin))
        mBinding.searchParametersRecyclerView.adapter = mSearchParametersAdapter
    }


    private fun setUpLocationAdapter() {
        mLocationAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                AnimatorInflater.loadAnimator(context, R.animator.animator_item_removed).apply {
                    mBinding.recyclerAndPositionCounter.positionCounter.itemCountTextView.apply {
                        setUpViewPivotForAnimation(this)
                        text = mLocationAdapter.itemCount.toString()
                        setTarget(this)
                    }
                    start()
                }
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                mBinding.recyclerAndPositionCounter.positionCounter.itemCountTextView.text =
                        (mLocationAdapter.itemCount).toString()
            }
        })
    }

    private fun setUpLocationListRecyclerView() {
        mBinding.recyclerAndPositionCounter.locationListRecyclerView.apply {
            mItemTouch.attachToRecyclerView(this)
            addItemDecoration(mItemDecorator)
            layoutManager = mLayoutManager
            adapter = mLocationAdapter
            attachSnapHelperWithListener(
                    mSnapHelper,
                    onSnapPositionChangeListener = this@LocationSearchFragment
            )
        }
    }

    private fun setUpViewPivotForAnimation(view: View) {
        view.apply {
            pivotX = 0f
            pivotY = height.toFloat()
        }
    }

    companion object {
        const val searchParamsMargin = 15

    }

}

fun RecyclerView.attachSnapHelperWithListener(
        snapHelper: SnapHelper,
        behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
        onSnapPositionChangeListener: OnSnapPositionChangeListener
) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener =
            SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
    addOnScrollListener(snapOnScrollListener)
}

