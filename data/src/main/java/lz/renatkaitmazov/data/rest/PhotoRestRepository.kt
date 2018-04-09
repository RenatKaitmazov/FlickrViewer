package lz.renatkaitmazov.data.rest

import io.reactivex.Flowable
import io.reactivex.Single
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
  private val cache: Cache<List<RecentPhotoEntity>>,
  private val mapper: Mapper<List<RecentPhotoResponse>, List<RecentPhotoEntity>>
) : IPhotoRestRepository {

  companion object {
    /**
     * Used as a key to store the server response in the [cache].
     */
    private const val KEY_PHOTO_REST_REPOSITORY = "PhotoRestRepository"
    private const val METHOD_RECENT = "flickr.photos.getRecent"
    private const val PHOTOS_PER_PAGE = 50
    private const val FORMAT_JSON = "json"
    private const val NO_JSON_CALLBACK = 1
    /**
     * A url for a thumbnail.
     * A url for a medium size image.
     * A url for the original image.
     */
    private const val EXTRAS = "url_s,url_c,url_o"
    private const val MSG_EXCEPTION_NO_CONNECTION = "There is no internet connection"
  }

  private val photoRestApi = retrofit.create(PhotoRestApi::class.java)

  override fun getRecentPhotos(currentPage: Int): Single<List<RecentPhotoEntity>> {
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
        // Check if there is an Internet connection.
        if (!connectivityChecker.hasInternetConnection()) {
          throw NoInternetConnectionException(MSG_EXCEPTION_NO_CONNECTION)
        }
        // The data was not previously saved, make an http request to get it from the server.
        photoRestApi.getRecentPhotos(
          METHOD_RECENT,
          API_KEY,
          PHOTOS_PER_PAGE,
          currentPage,
          FORMAT_JSON,
          NO_JSON_CALLBACK,
          EXTRAS
        ).toFlowable()
          .flatMap { Flowable.fromIterable(it.photosMetaData.photoList) }
          .filter { it.thumbnailImageUrl.isNotBlank() }
          .filter { it.mediumSizeImageUrl.isNotBlank() }
          .filter { it.originalImageUrl.isNotBlank() }
          .toList()
          .subscribe({ photoResponseList ->
            val photoEntities = mapper.map(photoResponseList)
            cache.put(KEY_PHOTO_REST_REPOSITORY, photoEntities)
            if (!emitter.isDisposed) {
              emitter.onSuccess(photoEntities)
            }
          }, { error ->
            if (!emitter.isDisposed) {
              emitter.onError(error)
            }
          })
      } catch (error: Throwable) {
        if (!emitter.isDisposed) {
          emitter.onError(error)
        }
      }
    }
  }
}