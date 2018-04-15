package lz.renatkaitmazov.flickrviewer.photolist.model

/**
 * Contains a url of a thumbnail that will be shown in a recycler view.
 *
 * @author Renat Kaitmazov
 */
data class PhotoListThumbnailItem(
  val id: String,
  val thumbnailUrl: String)
  : PhotoListAdapterItem() {
}