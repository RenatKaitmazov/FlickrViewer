package lz.renatkaitmazov.flickrviewer.photolist

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lz.renatkaitmazov.data.NAMED_FRG_COMPOSITE_DISPOSABLE
import lz.renatkaitmazov.data.NAMED_FRG_PHOTO_LIST_ADAPTER_ITEM
import lz.renatkaitmazov.data.model.entity.PhotoEntity
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
  private val mapper: @JvmSuppressWildcards Mapper<List<PhotoEntity>, List<PhotoListAdapterItem>>
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
      .doOnSubscribe {
        ifViewNotNull {
          it.resetState()
          it.restoreDefaultMethod()
        }
      }
      .doFinally { view?.hideProgress() }
      .map(mapper::map)
      .subscribe({ response ->
        view?.setRecentTitle()
        handleFirstPageSuccess(response)
      }, this::handleFirstPageError)
    subscriptionManager.add(updateDisposable)
  }

  override fun getNextPage(page: Int) {
    getNextPage(repository.getNextPage(page))
  }

  override fun onToolbarDoubleTap() {
    view?.scrollToFirstItem()
  }

  override fun search(query: String) {
    val searchDisposable = repository.search(query)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe {
        ifViewNotNull {
          it.resetState()
          it.showProgress()
        }
      }
      .doFinally { view?.hideProgress() }
      .map(mapper::map)
      .subscribe({ response ->
        view?.setSearchTitle(query)
        handleFirstPageSuccess(response)
      }, this::handleFirstPageError)
    subscriptionManager.add(searchDisposable)
  }

  override fun searchNextPage(query: String, page: Int) {
    getNextPage(repository.searchNextPage(query, page))
  }

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun getNextPage(nextPageDataSource: Single<List<PhotoEntity>>) {
    val nextPageDisposable = nextPageDataSource
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

  private fun handleFirstPageSuccess(response: List<PhotoListAdapterItem>) {
    ifViewNotNull {
      it.hideAnyVisibleError()
      if (response.isEmpty()) {
        it.onFirstPageEmptyResponseError()
        return@ifViewNotNull
      }
      it.showThumbnails(response)
    }
  }

  private fun handleFirstPageError(error: Throwable) {
    Timber.e(error)
    view?.onFirstPageNetworkError()
  }
}