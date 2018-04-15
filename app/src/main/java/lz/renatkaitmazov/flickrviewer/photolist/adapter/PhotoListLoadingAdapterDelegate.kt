package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.adapter.AdapterDelegate
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListLoadingItem

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListLoadingAdapterDelegate : AdapterDelegate<List<PhotoListAdapterItem>> {

  override fun isForViewType(items: List<PhotoListAdapterItem>, position: Int): Boolean {
    return items[position] is PhotoListLoadingItem
  }

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    val ctx = parent.context
    val inflater = LayoutInflater.from(ctx)
    val view = inflater.inflate(R.layout.item_photo_list_loading, parent, false)
    return PhotoListLoadingViewHolder(view)
  }

  override fun onBindViewHolder(items: List<PhotoListAdapterItem>,
                                position: Int,
                                viewHolder: RecyclerView.ViewHolder) {
    // The loading item just shows some progress.
    // There is nothing to bind.
  }
}