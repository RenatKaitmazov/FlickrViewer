package lz.renatkaitmazov.data.rest

import io.reactivex.Single
import lz.renatkaitmazov.data.model.entity.PhotoEntity

/**
 * Defines a list of methods to get photos.
 *
 * @author Renat Kaitmazov
 */
interface IPhotoRestRepository {
  /**
   * Returns a list of recent photos.
   *
   * @return a list of recent photos.
   */
  fun getRecentPhotosAtFirstPage(): Single<List<PhotoEntity>>

  /**
   * Invalidates the cache and fetches the most recent photos.
   */
  fun updatePhotoList(): Single<List<PhotoEntity>>

  /**
   * Returns photos at the given page.
   */
  fun getNextPage(page: Int): Single<List<PhotoEntity>>

  /**
   * Returns photos found by the given query.
   */
  fun search(query: String): Single<List<PhotoEntity>>
}