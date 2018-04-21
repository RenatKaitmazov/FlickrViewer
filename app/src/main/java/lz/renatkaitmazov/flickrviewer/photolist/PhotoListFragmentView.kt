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
   * Resets the state of the view.
   */
  fun resetState()

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
   * Handles a network error that occurred while fetching the first page of thumbnails.
   */
  fun onFirstPageNetworkError()

  /**
   * Handles a network error that occurred while fetching a new page of thumbnails.
   */
  fun onNextPageNetworkError()

  /**
   * Handles an empty response when fetching the first page of thumbnails.
   */
  fun onFirstPageEmptyResponseError()

  /**
   * Handles an empty response when fetching a new page of thumbnails.
   */
  fun onNextPageEmptyResponseError()

  /**
   * Hides any error that is currently visible on the screen.
   */
  fun hideAnyVisibleError()

  /**
   * Scrolls the list of thumbnails to the very first item.
   */
  fun scrollToFirstItem()
}