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
   *
   * @param page used for pagination.
   */
  fun getPhotoList(page: Int)

  /**
   * Erases the current list of photos and returns a brand new one from the server.
   */
  fun updatePhotoList()
}