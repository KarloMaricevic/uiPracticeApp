package my.app.uiPracticeApp.utils.recyclerViewUtils.touchHelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import my.app.uiPracticeApp.adapters.DeleteItemInterface

class OnSwipeUpDeleteSimpleCallback(private val deleteItemInterface: DeleteItemInterface) :
    ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.UP
    ) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val itemPosition = viewHolder.adapterPosition
        deleteItemInterface.deleteItem(itemPosition)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }
}
