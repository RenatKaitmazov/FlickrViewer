package lz.renatkaitmazov.data.model.photo

import com.google.gson.annotations.SerializedName

/**
 * This class is used to parse a response from the server when the user
 * requests a list of recent public photos. Apart from the list of photos
 * the response contains other useful metadata like the number of pages,
 * total number of photos, etc.
 *
 * @author Renat Kaitmazov
 */
data class PhotosResponse(
  @SerializedName("photos")
  val photosMetaData: PhotosMetaDataResponse,
  @SerializedName("stat")
  val status: String
)