package lz.renatkaitmazov.flickrviewer.photolist

import lz.renatkaitmazov.flickrviewer.base.Presenter

/**
 * Implementations of this class contains the logic of [PhotoListFragment] view.
 *
 * @author Renat Kaitmazov
 */
interface IPhotoListFragmentPresenter : Presenter<PhotoListFragmentView> {

  /**
   * Gets a list of photos from the data layer.
   * The list does not contain actual photos. It contains URLs to photos.
   */
  fun getPhotoListAtFirstPage()

  /**
   * Erases the current list of photos and returns a brand new one from the server.
   */
  fun updatePhotoList()

  /**
   * Gets a list of photos at the specified page.
   * The list does not contain actual photos. It contains URLs to photos.
   *
   * @param page a page at which needed photos are located.
   * @param forMethod Flickr API method.
   */
  fun getNextPage(page: Int)

  /**
   * This method gets triggered when the user taps toolbar two times in a row.
   */
  fun onToolbarDoubleTap()

  /**
   * Searches for photos by the given query.
   *
   * @param query a searchFirstPage query
   */
  fun search(query: String)

  /**
   * Returns a list of photos found be the given query [query] at the given page [page].
   */
  fun searchNextPage(query: String, page: Int)
}