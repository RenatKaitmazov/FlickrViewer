package lz.renatkaitmazov.flickrviewer.base

/**
 * Defines basic operations common to all presenters.
 *
 * @author Renat Kaitmazov
 */
interface Presenter<View : LoadingView> {
  /**
   * Releases a reference to an object of type [View].
   * Perform clean up here.
   */
  fun unbind()
}