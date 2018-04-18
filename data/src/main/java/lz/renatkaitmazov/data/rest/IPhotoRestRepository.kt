package lz.renatkaitmazov.data.rest

import io.reactivex.Single
import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity

/**
 * Defines a list of methods to get photos.
 *
 * @author Renat Kaitmazov
 */
interface IPhotoRestRepository {
  /**
   * Returns a list of recent photos.
   *
   * @param currentPage used for pagination.
   * @return a list of recent photos.
   */
  fun getRecentPhotos(currentPage: Int): Single<List<RecentPhotoEntity>>

  /**
   * Invalidates the cache and fetches the most recent photos.
   */
  fun updatePhotoList(): Single<List<RecentPhotoEntity>>
}