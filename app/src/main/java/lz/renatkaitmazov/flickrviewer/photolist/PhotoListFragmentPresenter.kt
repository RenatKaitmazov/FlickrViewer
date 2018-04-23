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

  /*------------------------------------------------------------------------*/
  /* IPhotoListFragmentPresenter implementation                             */
  /*------------------------------------------------------------------------*/

  override fun getPhotoListAtFirstPage() {
    val getListDisposable = repository.getPhotoListAtFirstPage()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.showProgress() }
      .doFinally { view?.hideProgress() }
      .map(mapper::map)
      .subscribe(this::handleFirstPageSuccess, this::handleFirstPageError)
    subscriptionManager.add(getListDisposable)
  }

  override fun updatePhotoList() {
    val updateDisposable = repository.updatePhotoList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.resetState() }
      .doFinally { view?.hideProgress() }
      .map(mapper::map)
      .subscribe(this::handleFirstPageSuccess, this::handleFirstPageError)
    subscriptionManager.add(updateDisposable)
  }

  override fun getNextPage(page: Int) {
    val nextPageDisposable = repository.getNextPage(page)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { view?.addLoadingItem() }
      .map(mapper::map)
      .subscribe({ response ->
        ifViewNotNull {
          if (response.isEmpty()) {
            it.onNextPageEmptyResponseError()
            return@ifViewNotNull
          }
          it.removeLoadingItem()
          it.showNextPageThumbnails(response)
        }
      }, { error ->
        Timber.e(error)
        ifViewNotNull {
          it.removeLoadingItem()
          it.onNextPageNetworkError()
        }
      })
    subscriptionManager.add(nextPageDisposable)
  }

  override fun onToolbarDoubleTap() {
    view?.scrollToFirstItem()
  }

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun handleFirstPageSuccess(response: List<PhotoListAdapterItem>) {
    ifViewNotNull {
      if (response.isEmpty()) {
        it.onFirstPageEmptyResponseError()
        return@ifViewNotNull
      }
      it.hideAnyVisibleError()
      it.showThumbnails(response)
    }
  }

  private fun handleFirstPageError(error: Throwable) {
    Timber.e(error)
    view?.onFirstPageNetworkError()
  }
}