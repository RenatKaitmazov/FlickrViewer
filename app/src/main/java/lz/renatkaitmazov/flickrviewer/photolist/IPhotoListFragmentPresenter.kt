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
   * @param page
   */
  fun getNextPage(page: Int)
}