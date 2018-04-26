package lz.renatkaitmazov.flickrviewer.photodetail

import lz.renatkaitmazov.flickrviewer.base.Presenter

/**
 *
 * @author Renat Kaitmazov
 */
interface IPhotoDetailFragmentPresenter : Presenter<PhotoDetailFragmentView> {

  /**
   * Creates an image file in the cache so that it can be shared.
   *
   * @param imageUrl a url from which the image can be downloaded.
   * @param subDirectoryName  the name of a subdirectory in the cache where images specific to this
   *                          app are stored.
   */
  fun shareImage(imageUrl: String, subDirectoryName: String)
}