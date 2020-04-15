package my.app.uiPracticeApp.adapters.locationAdapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import my.app.uiPracticeApp.R
import my.app.uiPracticeApp.data.entities.Location
import my.app.uiPracticeApp.databinding.ItemLocationBinding

class LocationAdapter(fragmentContext: Context, activity: Activity) :
    RecyclerView.Adapter<ItemLocationViewHolder>() {

    // workaround for not measured views in itemDecorator

    private val mViewWidth: Int
    private val mViewHight: Int

    init {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels

        mViewWidth = (deviceWidth * widthToDeviceWidthFactor).toInt()
        mViewHight = (mViewWidth * hightToWidthFactor).toInt()
    }

    private var mLocationsList = arrayListOf<Location>()

    private val mGlide = Glide.with(fragmentContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLocationViewHolder {
        val mBinding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mBinding.root.layoutParams.apply {
            width = mViewWidth
            height = mViewHight
        }
        return ItemLocationViewHolder(
            mBinding
        )
    }

    override fun getItemCount(): Int = mLocationsList.count()

    override fun onBindViewHolder(holder: ItemLocationViewHolder, position: Int) {

        holder.bindData(mLocationsList[position])
        mGlide
            .load(R.mipmap.eiffel_tower)
            .optionalCenterCrop()
            .into(TargetView(holder.getPictureView()))
    }

    fun setData(locationList: List<Location>) {
        val diffResult =
            DiffUtil.calculateDiff(LocationAdapterDiffUtil(mLocationsList, locationList))
        mLocationsList = ArrayList(locationList)
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        const val widthToDeviceWidthFactor = 0.902
        const val hightToWidthFactor = 1.287
    }
}

class ItemLocationViewHolder(private val mBinding: ItemLocationBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bindData(location: Location) {
        mBinding.location = location
        mBinding.executePendingBindings()
    }

    fun getPictureView(): View {
        return mBinding.itemLocationConstraintLayout
    }
}

class TargetView(private val view: View) : CustomTarget<Drawable>() {
    override fun onLoadCleared(placeholder: Drawable?) {
        view.background = placeholder
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        view.background = resource
    }
}

class LocationAdapterDiffUtil(
    private val mOldLocationList: List<Location>,
    private val mNewLocationList: List<Location>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldLocationList.count()
    }

    override fun getNewListSize(): Int {
        return mNewLocationList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldLocationList[oldItemPosition].id == mNewLocationList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldLocationList[oldItemPosition] == mNewLocationList[newItemPosition]
    }
}
