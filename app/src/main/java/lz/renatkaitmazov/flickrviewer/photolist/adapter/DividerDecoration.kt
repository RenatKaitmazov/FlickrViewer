package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *
 * @author Renat Kaitmazov
 */
class DividerDecoration(
  private val clearance: Int,
  private val spanCount: Int
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect,
                              view: View,
                              parent: RecyclerView,
                              state: RecyclerView.State?) {
    val position = parent.getChildAdapterPosition(view)
    // Go clockwise and set clearance if possible.
    val isFirstRow = position < spanCount
    val isLastColumn = (spanCount - 1) == (position % spanCount)
    val isLastRow = position >= (parent.adapter.itemCount - spanCount)
    val isFirstColumn = (position % spanCount) == 0

    if (!isFirstRow) {
      outRect.top = clearance
    }
    if (!isLastColumn) {
      outRect.right = clearance
    }
    if (!isLastRow) {
      outRect.bottom = clearance
    }
    if (!isFirstColumn) {
      outRect.left = clearance
    }
  }
}