package my.app.uiPracticeApp.adapters.locationAdapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LocationAdapterItemDecorator : RecyclerView.ItemDecoration() {

    @Suppress("MagicNumber")
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val parentWidth = parent.measuredWidth

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(
                (parentWidth - view.layoutParams.width) / 2,
                0,
                (parentWidth - view.layoutParams.width) / 4,
                0
            )
        } else if (parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount?.minus(1)) ?: parent.childCount) {
            outRect.set(
                (parentWidth - view.layoutParams.width) / 4,
                0,
                (parentWidth - view.layoutParams.width) / 2,
                0
            )
        } else {
            outRect.set(
                (parentWidth - view.layoutParams.width) / 4,
                0,
                (parentWidth - view.layoutParams.width) / 4,
                0
            )
        }
    }
}
