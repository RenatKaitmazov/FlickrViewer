package lz.renatkaitmazov.flickrviewer.photolist.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Contains a url of a thumbnail that will be shown in a recycler view.
 *
 * @author Renat Kaitmazov
 */
data class PhotoListThumbnailItem(
  val id: String,
  val thumbnailUrl: String,
  val mediumSizeImageUrl: String
) : PhotoListAdapterItem(),
  Parcelable {

  companion object {

    @JvmStatic
    val CREATOR = object : Parcelable.Creator<PhotoListAdapterItem> {
      override fun createFromParcel(source: Parcel): PhotoListAdapterItem {
        return PhotoListThumbnailItem(source)
      }

      override fun newArray(size: Int): Array<PhotoListAdapterItem?> {
        return arrayOfNulls(size)
      }
    }
  }

  private constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readString(),
    parcel.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeString(id)
    dest.writeString(thumbnailUrl)
    dest.writeString(mediumSizeImageUrl)
  }
}