package lz.renatkaitmazov.data.repository

import io.reactivex.Single
import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import lz.renatkaitmazov.data.rest.IPhotoRestRepository

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoRepository(
  private val restRepository: IPhotoRestRepository
) : IPhotoRepository {

  override fun getPhotoListAtFirstPage(): Single<List<RecentPhotoEntity>> {
    return restRepository.getRecentPhotosAtFirstPage()
  }

  override fun updatePhotoList(): Single<List<RecentPhotoEntity>> {
    return restRepository.updatePhotoList()
  }

  override fun getNextPage(page: Int): Single<List<RecentPhotoEntity>> {
    return restRepository.getNextPage(page)
  }
}