package lz.renatkaitmazov.data.model.entity

/**
 *
 * @author Renat Kaitmazov
 */
data class RecentPhotoEntity(
  val id: String,
  val title: String,
  val isPublic: Int,
  val thumbnailUrl: String,
  val mediumSizeImageUrl: String,
  val originalSizeImageUrl: String
)