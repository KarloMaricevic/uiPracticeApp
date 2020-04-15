package my.app.uiPracticeApp.adapters.searchParametars

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SearchParametersItemDecorator(private val marginFromSides: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(marginFromSides / 2, 0, marginFromSides / 2, 0)
    }
}
