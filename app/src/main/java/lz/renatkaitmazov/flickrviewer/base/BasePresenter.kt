package lz.renatkaitmazov.flickrviewer.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Implements basic operations common to all presenters.
 *
 * @author Renat Kaitmazov
 */
abstract class BasePresenter<View : LoadingView>(
  protected var view: View?,
  protected val subscriptionManager: CompositeDisposable
) : Presenter<View> {

  override fun unbind() {
    view = null
    subscriptionManager.clear()
  }
}