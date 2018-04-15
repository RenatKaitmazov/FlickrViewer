package lz.renatkaitmazov.flickrviewer.photolist

import lz.renatkaitmazov.flickrviewer.base.LoadingView
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem

/**
 * Provides an API for implementations of [IPhotoListFragmentPresenter] to interact with
 * [PhotoListFragment].
 *
 * @author Renat Kaitmazov
 */
interface PhotoListFragmentView : LoadingView {
  /**
   * Shows the given data to the user.
   */
  fun showThumbnails(thumbnails: List<PhotoListAdapterItem>)
}