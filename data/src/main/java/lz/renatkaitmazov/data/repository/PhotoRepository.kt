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

  override fun getPhotoList(page: Int): Single<List<RecentPhotoEntity>> {
    return restRepository.getRecentPhotos(page)
  }

  override fun updatePhotoList(): Single<List<RecentPhotoEntity>> {
    return restRepository.updatePhotoList()
  }
}