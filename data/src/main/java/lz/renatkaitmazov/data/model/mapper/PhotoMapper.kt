package lz.renatkaitmazov.data.model.mapper

import lz.renatkaitmazov.data.model.entity.PhotoEntity
import lz.renatkaitmazov.data.model.photo.PhotoResponse

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoMapper : Mapper<List<PhotoResponse>, List<PhotoEntity>> {

  override fun map(source: List<PhotoResponse>): List<PhotoEntity> {
    val size = source.size
    val entityList = ArrayList<PhotoEntity>(size)
    for (i in 0 until size) {
      val response = source[i]
      val entity = PhotoEntity(
        response.id,
        response.title,
        response.isPublic,
        response.thumbnailImageUrl!!,
        response.mediumSizeImageUrl!!,
        response.originalImageUrl!!
      )
      entityList.add(entity)
    }
    return entityList
  }
}