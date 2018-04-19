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

  /**
   * Adds an item into a list of thumbnails that shows some progress of loading.
   */
  fun addLoadingItem()

  /**
   * Removes an item added via [addLoadingItem].
   */
  fun removeLoadingItem()

  /**
   * Shows a new pack of thumbnails along with the old ones.
   *
   * @param thumbnails a new pack of thumbnails to be shown.
   */
  fun showNextPageThumbnails(thumbnails: List<PhotoListAdapterItem>)

  /**
   * Shows an error that occurred while fetching a new page of thumbnails.
   */
  fun showNextPageError()
}