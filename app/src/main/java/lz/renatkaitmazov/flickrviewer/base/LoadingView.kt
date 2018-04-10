package lz.renatkaitmazov.flickrviewer.base

/**
 * Any view which is capable of showing some content should implement
 * this interface if the view has to wait for the content to be retrieved
 * from either DB or the Internet.
 *
 * @author Renat Kaitmazov
 */
interface LoadingView {
  /**
   * Shows progress while its content is being retrieved.
   */
  fun showProgress()

  /**
   * Hides progress after its content has been downloaded.
   */
  fun hideProgress();
}