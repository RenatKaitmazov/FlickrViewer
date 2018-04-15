package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.adapter.AdapterDelegate
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListThumbnailItem

/**
 * Manages the creation of view holders if the data type is of type [PhotoListThumbnailItem].
 *
 * @author Renat Kaitmazov
 */
class PhotoListThumbnailAdapterDelegate : AdapterDelegate<List<PhotoListAdapterItem>> {

  override fun isForViewType(items: List<PhotoListAdapterItem>, position: Int): Boolean {
    return items[position] is PhotoListThumbnailItem
  }

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    val ctx = parent.context
    val inflater = LayoutInflater.from(ctx)
    val view = inflater.inflate(R.layout.item_photo_list_thumbnail, parent, false)
    return PhotoListThumbnailViewHolder(view)
  }

  override fun onBindViewHolder(items: List<PhotoListAdapterItem>,
                                position: Int,
                                viewHolder: RecyclerView.ViewHolder) {
    val holder = viewHolder as PhotoListThumbnailViewHolder
    val item = items[position] as PhotoListThumbnailItem
    val ctx = holder.itemView.context
    Glide.with(ctx)
      .load(item.thumbnailUrl)
      .into(holder.thumbnailImageView)
  }
}