package lz.renatkaitmazov.data.model.photo

import com.google.gson.annotations.SerializedName

/**
 * This class is used to parse a response from the server.
 * Contains metadata like number of photos, number of pages, photos per page, etc.
 *
 * @author Renat Kaitmazov
 */
data class PhotosMetaDataResponse(
  @SerializedName("page")
  val currentPage: Int,
  @SerializedName("pages")
  val numberOfPages: Int,
  @SerializedName("perpage")
  val photosPerPage: Int,
  @SerializedName("total")
  val numberOfPhotos: Int,
  @SerializedName("photo")
  val photoList: List<PhotoResponse>
)