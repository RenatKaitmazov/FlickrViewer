package lz.renatkaitmazov.flickrviewer.photolist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lz.renatkaitmazov.data.NAMED_FRG_COMPOSITE_DISPOSABLE
import lz.renatkaitmazov.data.NAMED_FRG_PHOTO_LIST_ADAPTER_ITEM
import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import lz.renatkaitmazov.data.model.mapper.Mapper
import lz.renatkaitmazov.data.repository.IPhotoRepository
import lz.renatkaitmazov.flickrviewer.base.BasePresenter
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import timber.log.Timber
import javax.inject.Named

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListFragmentPresenter(
  view: PhotoListFragmentView,
  @Named(NAMED_FRG_COMPOSITE_DISPOSABLE)
  subscriptionManager: CompositeDisposable,
  private val repository: IPhotoRepository,
  @Named(NAMED_FRG_PHOTO_LIST_ADAPTER_ITEM)
  private val mapper: @JvmSuppressWildcards Mapper<List<RecentPhotoEntity>, List<PhotoListAdapterItem>>
) : BasePresenter<PhotoListFragmentView>(view, subscriptionManager),
  IPhotoListFragmentPresenter {

  override fun getPhotoListAtFirstPage() {
    val getListDisposable = repository.getPhotoListAtFirstPage()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.showProgress() }
      .doFinally { view?.hideProgress() }
      .map(mapper::map)
      .subscribe({ view?.showThumbnails(it) }, Timber::e)
    subscriptionManager.add(getListDisposable)
  }

  override fun updatePhotoList() {
    val updateDisposable = repository.updatePhotoList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doFinally { view?.hideProgress() }
      .map(mapper::map)
      .subscribe({ view?.showThumbnails(it) }, Timber::e)
    subscriptionManager.add(updateDisposable)
  }

  override fun getNextPage(page: Int) {
    val nextPageDisposable = repository.getNextPage(page)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.addLoadingItem() }
      .map(mapper::map)
      .subscribe({
        val view = this.view
        if (view != null) {
          view.removeLoadingItem()
          view.showNextPageThumbnails(it)
        }
      }, {error ->
        Timber.e(error)
        val view = this.view
        if (view != null) {
          view.removeLoadingItem()
          view.showNextPageError()
        }
      })
    subscriptionManager.add(nextPageDisposable)
  }
}