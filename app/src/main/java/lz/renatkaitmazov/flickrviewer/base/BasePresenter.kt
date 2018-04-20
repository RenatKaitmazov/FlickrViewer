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

  /**
   * Performs the given task if the view associated with this presenter is not null.
   */
  protected fun ifViewNotNull(task: (View) -> Unit) {
    val view = this.view
    if (view != null) {
      task(view)
    }
  }
}