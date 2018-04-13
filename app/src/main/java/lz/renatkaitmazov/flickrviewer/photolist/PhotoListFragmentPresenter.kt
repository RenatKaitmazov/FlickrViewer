package lz.renatkaitmazov.flickrviewer.photolist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lz.renatkaitmazov.data.repository.IPhotoRepository
import lz.renatkaitmazov.flickrviewer.base.BasePresenter
import timber.log.Timber

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListFragmentPresenter(
  view: PhotoListFragmentView,
  subscriptionManager: CompositeDisposable,
  private val repository: IPhotoRepository
) : BasePresenter<PhotoListFragmentView>(view, subscriptionManager),
  IPhotoListFragmentPresenter {

  override fun getPhotoList(page: Int) {
    val disposable = repository.getPhotoList(page)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.showProgress() }
      .doFinally { view?.hideProgress() }
      .subscribe({ Timber.d(it.toString()) }, Timber::e)
    subscriptionManager.add(disposable)
  }
}