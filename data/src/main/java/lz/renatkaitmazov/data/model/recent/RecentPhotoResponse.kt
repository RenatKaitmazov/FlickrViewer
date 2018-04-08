package lz.renatkaitmazov.data.model.recent

import com.google.gson.annotations.SerializedName

/**
 * This is a class used to parse a response from the server.
 * Contains information about an image like [id], which is used
 * to get a link to the actual image.
 *
 * @author Renat Kaitmazov
 */
data class RecentPhotoResponse(
  val id: String,
  @SerializedName("owner")
  val ownerId: String,
  val secret: String,
  val title: String,
  @SerializedName("ispublic")
  val isPublic: Int,
  val thumbnailImageUrl: String,
  val mediumSizeImageUrl: String
) {

  override fun equals(other: Any?): Boolean {
    if (other == this) return true
    if (other == null || other !is RecentPhotoResponse) return false
    return this.id == other.id
  }

  override fun hashCode(): Int {
    var hashCode = 17
    hashCode = 31 * hashCode + id.hashCode()
    return hashCode
  }
}