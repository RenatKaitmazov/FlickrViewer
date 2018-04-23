package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.content.Context
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
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
  private val context: Context,
  private val thumbnailClickListener: PhotoListThumbnailViewHolder.OnThumbnailClickListener?
) : AdapterDelegate<List<PhotoListAdapterItem>> {

  override fun isForViewType(items: List<PhotoListAdapterItem>, position: Int): Boolean {
    return items[position] is PhotoListThumbnailItem
  }

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.item_photo_list_thumbnail, parent, false)
    return PhotoListThumbnailViewHolder(view, thumbnailClickListener)
  }

  override fun onBindViewHolder(items: List<PhotoListAdapterItem>,
                                position: Int,
                                viewHolder: RecyclerView.ViewHolder) {
    val holder = viewHolder as PhotoListThumbnailViewHolder
    val item = items[position] as PhotoListThumbnailItem
    holder.bind(context, item)
  }
}