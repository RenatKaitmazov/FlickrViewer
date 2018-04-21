package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.adapter.AdapterDelegate
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListThumbnailItem

/**
 * Manages the creation of view holders if the data type is of type [PhotoListThumbnailItem].
 *
 * @author Renat Kaitmazov
 */
class PhotoListThumbnailAdapterDelegate(
  private val context: Context
) : AdapterDelegate<List<PhotoListAdapterItem>> {

  override fun isForViewType(items: List<PhotoListAdapterItem>, position: Int): Boolean {
    return items[position] is PhotoListThumbnailItem
  }

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.item_photo_list_thumbnail, parent, false)
    return PhotoListThumbnailViewHolder(view)
  }

  override fun onBindViewHolder(items: List<PhotoListAdapterItem>,
                                position: Int,
                                viewHolder: RecyclerView.ViewHolder) {
    val holder = viewHolder as PhotoListThumbnailViewHolder
    val item = items[position] as PhotoListThumbnailItem
    val requestOptions = RequestOptions()
      .placeholder(R.drawable.ic_image_placeholder)
    Glide.with(context)
      .setDefaultRequestOptions(requestOptions)
      .load(item.thumbnailUrl)
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(holder.thumbnailImageView)
  }
}