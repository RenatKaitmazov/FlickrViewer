package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import lz.renatkaitmazov.flickrviewer.R

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListAdapter : RecyclerView.Adapter<PhotoListViewHolder>() {

  override fun getItemCount(): Int {
    return 4
  }

  override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
    val ctx = parent.context
    val inflater = LayoutInflater.from(ctx)
    val view = inflater.inflate(R.layout.item_photo_list, parent, false)
    return PhotoListViewHolder(view)
  }
}