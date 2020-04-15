package my.app.uiPracticeApp.utils.recyclerViewUtils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CardPageLinarManger(
        context: Context,
        orientation: Int = RecyclerView.VERTICAL,
        maxDistance: Int? = null,
        private val mMaxElevation: Float = 20f
) : LinearLayoutManager(context, orientation, false) {


    private val mMaxDistanceFromCenter: Int = maxDistance ?: -1

    private val mDistanceMeasure: LinearManagerDistanceMeasure = if (orientation == HORIZONTAL) {
        LinearManagerDistanceMeasure.createVerticalDistanceMeasure()
    } else LinearManagerDistanceMeasure.createHorizontalDistanceMeasure()


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
    ): Int {
        if (orientation == HORIZONTAL) {
            updateChildrenAlpha()
            updateChildrenTranslationZ()
        }
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun scrollVerticallyBy(
            dy: Int,
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
    ): Int {
        if (orientation == VERTICAL) {
            updateChildrenAlpha()
            updateChildrenTranslationZ()
        }
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        updateChildrenAlpha()
        updateChildrenTranslationZ()
    }


    private fun updateChildrenAlpha() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != null) {
                val centerOfChildVertical =
                        mDistanceMeasure.getChildCenterPositionRelativeToViewGroup(child, this)
                when {
                    mMaxDistanceFromCenter != -1 && mDistanceMeasure.getDistanceFromChildCenterToViewGroupCenter(
                            child,
                            this
                    ) >= mMaxDistanceFromCenter -> {
                        child.alpha = 0f
                    }
                    centerOfChildVertical <= 0 || centerOfChildVertical >= width -> {
                        child.alpha = 0f
                    }
                    centerOfChildVertical == mDistanceMeasure.getDistanceFromCenterToView(this) -> {
                        child.alpha = 1f
                    }
                    else -> {
                        val lengthPercentage = getDistancePercentage(child)
                        child.alpha = lengthPercentage
                    }
                }
            }
        }
    }

    private fun updateChildrenTranslationZ() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != null) {
                val centerOfChildVertical =
                        mDistanceMeasure.getChildCenterPositionRelativeToViewGroup(child, this)
                when {
                    mMaxDistanceFromCenter != -1 && mDistanceMeasure.getDistanceFromChildCenterToViewGroupCenter(
                            child,
                            this
                    ) >= mMaxDistanceFromCenter -> {
                        child.elevation = 0f
                    }
                    centerOfChildVertical >= width || centerOfChildVertical <= 0 -> {
                        child.elevation = 0f
                    }
                    centerOfChildVertical == mDistanceMeasure.getDistanceFromCenterToView(this) -> {
                        child.elevation = mMaxElevation
                    }
                    else -> {
                        val lengthPercentage = getDistancePercentage(child)
                        child.elevation = lengthPercentage * mMaxElevation
                    }
                }
            }
        }
    }

    @Suppress("MaxLineLength")
    private fun getDistancePercentage(child: View): Float {
        return if (mMaxDistanceFromCenter != -1 && mMaxDistanceFromCenter < mDistanceMeasure.getDistanceFromCenterToView(
                        this
                )
        ) {
            if (mDistanceMeasure.getChildCenterPositionRelativeToViewGroup(child, this)
                            .toInt() == mDistanceMeasure.getDistanceFromCenterToView(this).toInt()
            ) {
                1f
            } else {
                mDistanceMeasure.getDistancePercentageFromViewCenterToMaxDistanceFromLayoutCenter(child, this, mMaxDistanceFromCenter)
            }
        } else {
            mDistanceMeasure.getDistancePercentageFromClosestEdgeToView(child, this)
        }
    }
}
