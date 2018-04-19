package lz.renatkaitmazov.data.repository

import io.reactivex.Single
import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity

/**
 * Provides a general API to retrieve data.
 * Abstracts the underlying data provider be it DB or Network or file system.
 *
 * @author Renat Kaitmazov
 */
interface IPhotoRepository {

  /**
   * Returns a list of recent photos.
   *
   * @return a list of recent photos.
   */
  fun getPhotoListAtFirstPage(): Single<List<RecentPhotoEntity>>

  /**
   * Invalidates the cache and fetches the most recent photos.
   *
   * @return list of updates photos.
   */
  fun updatePhotoList(): Single<List<RecentPhotoEntity>>

  fun getNextPage(page: Int): Single<List<RecentPhotoEntity>>
}