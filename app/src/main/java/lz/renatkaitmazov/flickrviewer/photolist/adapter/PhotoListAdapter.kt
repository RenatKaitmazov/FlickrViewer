package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import lz.renatkaitmazov.flickrviewer.base.adapter.AdapterDelegate
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

  companion object {

    /**
     * An identifier for a view holder that displays a thumbnail image.
     */
    private const val TYPE_THUMBNAIL_ITEM = 1_000

    /**
     * An identifier for a view holder that displays some sort of a progress.
     */
    private const val TYPE_LOADING_ITEM = 1_100
  }

  /**
   * Contains items to be displayed in a recycler view.
   */
  private val items = ArrayList<PhotoListAdapterItem>()

  /**
   * Contains delegates that will provide and bind view holders.
   */
  private val delegates = HashMap<Int, AdapterDelegate<List<PhotoListAdapterItem>>>()

  constructor(ctx: Context) {
    delegates[TYPE_THUMBNAIL_ITEM] = PhotoListThumbnailAdapterDelegate(ctx)
    delegates[TYPE_LOADING_ITEM] = PhotoListLoadingAdapterDelegate(ctx)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun getItemViewType(position: Int): Int {
    for (viewType in delegates.keys) {
      val delegate = delegates[viewType]
      if (delegate != null) {
        if (delegate.isForViewType(items, position)) {
          return viewType
        }
      }
    }
    throw IllegalArgumentException("No view type for $position")
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val delegate = delegates[viewType] ?: throw IllegalArgumentException("Unknown view type: $viewType")
    return delegate.onCreateViewHolder(parent)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val viewType = holder.itemViewType
    val delegate = delegates[viewType] ?: throw IllegalArgumentException("Unknown view type: $viewType")
    delegate.onBindViewHolder(items, position, holder)
  }

  /**
   * Updates the data source by swapping old data with new data contained in [items].
   *
   * @param items a new list of data to be displayed.
   */
  fun update(items: List<PhotoListAdapterItem>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }
}