package lz.renatkaitmazov.flickrviewer.base.listener

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Detects double taps.
 *
 * @author Renat Kaitmazov
 */
class DoubleTapDetector(
  var listener: DoubleTapListener?,
  private val viewId: Int
) : GestureDetector.SimpleOnGestureListener() {

  /**
   * Clients must implements this interface if they want to be notified when a double tap event
   * occurs.
   */
  interface DoubleTapListener {
    /**
     * A callback method that will be triggered when a double tap event occurs.
     *
     * @param viewId the id of the view this detector is associated with.
     * @return true if the event is handled, false otherwise.
     */
    fun onDoubleTaped(viewId: Int): Boolean
  }

  override fun onDoubleTap(e: MotionEvent): Boolean {
    if (listener != null) {
      return listener!!.onDoubleTaped(viewId)
    }
    return super.onDoubleTap(e)
  }
}