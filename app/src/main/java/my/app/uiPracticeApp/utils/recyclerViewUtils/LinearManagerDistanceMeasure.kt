package my.app.uiPracticeApp.utils.recyclerViewUtils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

abstract class LinearManagerDistanceMeasure {

    abstract fun getDistanceFromCenterToView(layoutManager: RecyclerView.LayoutManager): Float

    abstract fun getChildCenterPositionRelativeToViewGroup(
        child: View,
        layoutManager: RecyclerView.LayoutManager
    ): Float

    fun getDistanceChildCenterToRecyclerViewCenter(
        child: View,
        layoutManager: RecyclerView.LayoutManager
    ): Float {
        return abs(
            getChildCenterPositionRelativeToViewGroup(
                child,
                layoutManager
            ) - getDistanceFromCenterToView(layoutManager)
        )
    }

    fun getDistanceFromChildCenterToViewGroupCenter(
        child: View,
        layoutManager: RecyclerView.LayoutManager
    ): Float {
        return abs(
            getChildCenterPositionRelativeToViewGroup(
                child,
                layoutManager
            ) - getDistanceFromCenterToView(layoutManager)
        )
    }

    fun getDistanceToCloserEdge(
        child: View,
        layoutManager: RecyclerView.LayoutManager
    ): Float {
        return abs(
            getDistanceFromCenterToView(layoutManager) - getDistanceFromChildCenterToViewGroupCenter(
                child,
                layoutManager
            )
        )
    }

    fun getDistancePercentageFromClosestEdgeToView(
        child: View,
        layoutManager: RecyclerView.LayoutManager
    ): Float {
        return getDistanceToCloserEdge(
            child,
            layoutManager
        ) / getDistanceFromCenterToView(
            layoutManager
        )
    }

    @Suppress("MaxLineLength")
    fun getDistancePercentageFromViewCenterToMaxDistanceFromLayoutCenter(
        child: View,
        layoutManager: RecyclerView.LayoutManager,
        distance: Int
    ): Float {
        return 1 - getDistanceChildCenterToRecyclerViewCenter(
            child,
            layoutManager
        ) / distance
    }

    companion object {

        fun createHorizontalDistanceMeasure(): LinearManagerDistanceMeasure {
            return object : LinearManagerDistanceMeasure() {
                override fun getDistanceFromCenterToView(layoutManager: RecyclerView.LayoutManager): Float {
                    return layoutManager.height / 2f
                }

                override fun getChildCenterPositionRelativeToViewGroup(
                    child: View,
                    layoutManager: RecyclerView.LayoutManager
                ): Float {
                    return layoutManager.getDecoratedTop(child) + abs(
                        layoutManager.getDecoratedTop(
                            child
                        ) - layoutManager.getDecoratedTop(child)
                    ) / 2f
                }
            }
        }

        fun createVerticalDistanceMeasure(): LinearManagerDistanceMeasure {

            return object : LinearManagerDistanceMeasure() {
                override fun getDistanceFromCenterToView(layoutManager: RecyclerView.LayoutManager): Float {
                    return layoutManager.width / 2f
                }

                override fun getChildCenterPositionRelativeToViewGroup(
                    child: View,
                    layoutManager: RecyclerView.LayoutManager
                ): Float {
                    return layoutManager.getDecoratedLeft(child) + (layoutManager.getDecoratedRight(
                        child
                    ) - layoutManager.getDecoratedLeft(
                        child
                    )) / 2f
                }
            }
        }
    }
}
