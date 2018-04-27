package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_photo_list_thumbnail.view.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListThumbnailItem

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListThumbnailViewHolder(
  view: View,
  private val thumbnailClickListener: OnThumbnailClickListener?
) : RecyclerView.ViewHolder(view),
  View.OnClickListener {

  interface OnThumbnailClickListener {
    /**
     * A callback method which is triggered when the user clicks this view holder.
     *
     * @param item a data item this view holder is associated with.
     * @param sharedView a view that is used when performing the shared element transition.
     */
    fun onThumbnailClicked(item: PhotoListThumbnailItem, sharedView: View)
  }

  companion object {

    /**
     * Contains an image placeholder which is displayed until an actual image is loaded.
     * Since the placeholder is the same for every image, it is initialized as a static field to
     * be memory efficient.
     */
    @JvmStatic
    private val REQUEST_OPTIONS = RequestOptions()
      .placeholder(R.drawable.ic_image_placeholder)

    /**
     * A cross fade animation which is played when the image placeholder is replaced by an actual
     * image.
     * Created as a static field to be memory efficient.
     */
    @JvmStatic
    private val CROSS_FADE_ANIM = DrawableTransitionOptions.withCrossFade()

  }

  init {
    view.setOnClickListener(this)
  }

  /**
   * A place where a thumbnail will be shown.
   */
  private val thumbnailImageView: ImageView = view.thumbnailImageView

  /**
   * A data item associated with this view holder.
   */
  private lateinit var item: PhotoListThumbnailItem

  /**
   * Binds the given data item to this view holder, so that the item's data will be shown
   * in this view holder.
   *
   * @param context see [Context].
   * @param item whose data should be shown in this view holder.
   */
  fun bind(context: Context, item: PhotoListThumbnailItem) {
    this.item = item
    Glide.with(context)
      .setDefaultRequestOptions(REQUEST_OPTIONS)
      .load(item.thumbnailUrl)
      .transition(CROSS_FADE_ANIM)
      .into(thumbnailImageView)
  }

  override fun onClick(v: View?) {
    thumbnailClickListener?.onThumbnailClicked(item, thumbnailImageView)
  }
}