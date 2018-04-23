package lz.renatkaitmazov.data.rest

import io.reactivex.Single
import io.reactivex.SingleEmitter
import lz.renatkaitmazov.data.cache.Cache
import lz.renatkaitmazov.data.device.IConnectivityChecker
import lz.renatkaitmazov.data.exception.NoInternetConnectionException
import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import lz.renatkaitmazov.data.model.mapper.Mapper
import lz.renatkaitmazov.data.model.recentphoto.RecentPhotoResponse
import retrofit2.Retrofit

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoRestRepository(
  retrofit: Retrofit,
  private val connectivityChecker: IConnectivityChecker,
  private val cache: @JvmSuppressWildcards Cache<MutableList<RecentPhotoEntity>>,
  private val mapper: @JvmSuppressWildcards Mapper<List<RecentPhotoResponse>, List<RecentPhotoEntity>>
) : IPhotoRestRepository {

  companion object {
    /**
     * Used as a key to store the server response in the [cache].
     */
    private const val KEY_PHOTO_REST_REPOSITORY = "PhotoRestRepository"
    private const val METHOD_RECENT = "flickr.photos.getRecent"
    private const val PHOTOS_PER_PAGE = 100
    private const val FORMAT_JSON = "json"
    private const val NO_JSON_CALLBACK = 1
    /**
     * url_s is for a thumbnail.
     * url_c is for a medium size image.
     * url_o is for the original image.
     */
    private const val EXTRAS = "url_s,url_c,url_o"
    private const val MSG_EXCEPTION_NO_CONNECTION = "There is no internet connection"
  }

  private val photoRestApi = retrofit.create(PhotoRestApi::class.java)

  /*------------------------------------------------------------------------*/
  /* IPhotoRestRepository implementation                                    */
  /*------------------------------------------------------------------------*/

  override fun getRecentPhotosAtFirstPage(): Single<List<RecentPhotoEntity>> {
    return Single.create { emitter ->
      if (emitter.isDisposed) {
        return@create
      }
      try {
        // First of all, check the cache.
        val cachedData = cache.get(KEY_PHOTO_REST_REPOSITORY)
        if (cachedData != null) {
          if (!emitter.isDisposed) {
            emitter.onSuccess(cachedData)
            return@create
          }
        }
        loadRecentPhotos(1)
          .subscribe({
            cache.put(KEY_PHOTO_REST_REPOSITORY, it as MutableList<RecentPhotoEntity>)
            if (!emitter.isDisposed) {
              emitter.onSuccess(it)
            }
          }, { error -> handleError(emitter, error) })
      } catch (error: Throwable) {
        handleError(emitter, error)
      }
    }
  }

  override fun updatePhotoList(): Single<List<RecentPhotoEntity>> {
    return Single.create { emitter ->
      if (emitter.isDisposed) {
        return@create
      }
      try {
        loadRecentPhotos(1)
          .subscribe({
            // Invalidate the cache only if the server responded successfully.
            invalidateCache()
            cache.put(KEY_PHOTO_REST_REPOSITORY, it as MutableList<RecentPhotoEntity>)
            if (!emitter.isDisposed) {
              emitter.onSuccess(it)
            }
          }, { error ->
            handleError(emitter, error)
          })
      } catch (error: Throwable) {
        handleError(emitter, error)
      }
    }
  }

  override fun getNextPage(page: Int): Single<List<RecentPhotoEntity>> {
    return Single.create { emitter ->
      if (emitter.isDisposed) {
        return@create
      }
      loadRecentPhotos(page)
        .subscribe({
          // If the user wants to receive the next page it is assumed that the first page is already
          // obtained and stored in the cache.
          val cachedList = cache.get(KEY_PHOTO_REST_REPOSITORY)
          cachedList?.addAll(it)
          if (!emitter.isDisposed) {
            emitter.onSuccess(it)
          }
        }, { error -> handleError(emitter, error) })
    }
  }

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun invalidateCache() {
    cache.remove(KEY_PHOTO_REST_REPOSITORY)
  }

  private fun loadRecentPhotos(page: Int): Single<List<RecentPhotoEntity>> {
    // Check to see if the device is connected to the Internet.
    if (!connectivityChecker.hasInternetConnection()) {
      throw NoInternetConnectionException(MSG_EXCEPTION_NO_CONNECTION)
    }
    val recent = METHOD_RECENT
    val key = API_KEY
    val perPage = PHOTOS_PER_PAGE
    val json = FORMAT_JSON
    val noCallback = NO_JSON_CALLBACK
    val extras = EXTRAS
    // Connect to the server to fetch photos.
    return photoRestApi.getRecentPhotos(recent, key, perPage, page, json, noCallback, extras)
      .toFlowable()
      .flatMapIterable { it.photosMetaData.photoList }
      .filter { it.thumbnailImageUrl != null }
      .filter { it.thumbnailImageUrl!!.isNotBlank() }
      .filter { it.mediumSizeImageUrl != null }
      .filter { it.mediumSizeImageUrl!!.isNotBlank() }
      .filter { it.originalImageUrl != null }
      .filter { it.originalImageUrl!!.isNotBlank() }
      .toList()
      .map(mapper::map)
  }

  private fun handleError(emitter: SingleEmitter<*>, error: Throwable) {
    if (!emitter.isDisposed) {
      emitter.onError(error)
    }
  }
}