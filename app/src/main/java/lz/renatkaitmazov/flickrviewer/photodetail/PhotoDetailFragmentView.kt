package lz.renatkaitmazov.flickrviewer.photodetail

import lz.renatkaitmazov.flickrviewer.base.LoadingView
import java.io.File

/**
 *
 * @author Renat Kaitmazov
 */
interface PhotoDetailFragmentView : LoadingView {

  /**
   * Notifies the view that the image to be shared is created and put into [imageFile].
   *
   * @param imageFile contains an image to be shared.
   */
  fun onImageFileInCacheCreated(imageFile: File)

  /**
   * Shows an error that happened while sharing an image.
   */
  fun showImageSharingError()
}