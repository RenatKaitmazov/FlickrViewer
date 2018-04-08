package lz.renatkaitmazov.data.model.mapper

import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import lz.renatkaitmazov.data.model.recentphoto.RecentPhotoResponse

/**
 *
 * @author Renat Kaitmazov
 */
class RecentPhotoMapper : Mapper<List<RecentPhotoResponse>, List<RecentPhotoEntity>> {

  override fun map(source: List<RecentPhotoResponse>): List<RecentPhotoEntity> {
    val size = source.size
    val entityList = ArrayList<RecentPhotoEntity>(size)
    for (i in 0 until size) {
      val response = source[i]
      val entity = RecentPhotoEntity(
        response.id,
        response.title,
        response.isPublic,
        response.thumbnailImageUrl,
        response.mediumSizeImageUrl,
        response.originalImageUrl
      )
      entityList[i] = entity
    }
    return entityList
  }
}