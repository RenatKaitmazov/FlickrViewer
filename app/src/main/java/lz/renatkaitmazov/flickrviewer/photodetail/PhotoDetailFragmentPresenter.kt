package lz.renatkaitmazov.flickrviewer.photodetail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import lz.renatkaitmazov.data.repository.IPhotoRepository
import lz.renatkaitmazov.flickrviewer.base.BasePresenter
import timber.log.Timber

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoDetailFragmentPresenter(
  view: PhotoDetailFragmentView,
  subscriptionManager: CompositeDisposable,
  private val photoRepository: IPhotoRepository
) : BasePresenter<PhotoDetailFragmentView>(view, subscriptionManager),
  IPhotoDetailFragmentPresenter {

  override fun shareImage(imageUrl: String, subDirectoryName: String) {
    val shareDisposable = photoRepository.createImageFileInCache(imageUrl, subDirectoryName)
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.showProgress() }
      .doFinally { view?.hideProgress() }
      .subscribe({
        view?.onImageFileInCacheCreated(it)
      }, {error ->
        Timber.e(error)
        view?.showImageSharingError()
      })
    subscriptionManager.add(shareDisposable)
  }
}