package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.item_photo_list_thumbnail.view.*

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListThumbnailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  /**
   * A place where a thumbnail will be shown.
   */
  val thumbnailImageView: ImageView = view.thumbnailImageView
}