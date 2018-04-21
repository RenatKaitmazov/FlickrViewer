package lz.renatkaitmazov.flickrviewer.base.listener

import android.support.v7.widget.RecyclerView

/**
 * When the user scrolls [RecyclerView] towards the bottom of the list, this class detects it and
 * if [Callback.canLoadMore] returns true, [Callback.loadMode] is triggered.
 *
 * @author Renat Kaitmazov
 */
class InfiniteBottomScroll(private val callback: Callback) : RecyclerView.OnScrollListener() {

  /**
   * Clients using this class must implement this interface so that this class can know
   * when to trigger [loadMode] method.
   */
  interface Callback {
    /**
     * Checks to see if it is possible to trigger [loadMode].
     *
     * @return true if it is possible, false otherwise.
     */
    fun canLoadMore(): Boolean

    /**
     * A hook method where the client specifies some logic. It is triggered if [canLoadMore]
     * returns true.
     */
    fun loadMode()
  }

  /**
   * A flag that checks the state of execution of a task.
   */
  private var isLoading = false

  override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
    if (dy > 0) {
      // The user scrolls to bottom.
      if (!isLoading && callback.canLoadMore()) {
        isLoading = true
        callback.loadMode()
      }
    }
  }

  /**
   * Must be called by the client when [Callback.loadMode] completes its work.
   */
  fun loadingCompleted() {
    isLoading = false
  }
}