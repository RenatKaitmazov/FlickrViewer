package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import lz.renatkaitmazov.flickrviewer.base.adapter.AdapterDelegate
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListLoadingItem

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

    @JvmStatic
    private val LOADING_ITEM: PhotoListAdapterItem = PhotoListLoadingItem()
  }

  /**
   * Contains items to be displayed in a recycler view.
   */
  private val items = ArrayList<PhotoListAdapterItem>()

  /**
   * Contains delegates that will provide and bind view holders.
   */
  private val delegates = HashMap<Int, AdapterDelegate<List<PhotoListAdapterItem>>>()

  constructor(ctx: Context,
              thumbnailClickListener: PhotoListThumbnailViewHolder.OnThumbnailClickListener?) {
    delegates[TYPE_THUMBNAIL_ITEM] = PhotoListThumbnailAdapterDelegate(ctx, thumbnailClickListener)
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

  /**
   * Appends the given list of items to the end of the current list of items without
   * deleting them.
   */
  fun append(items: List<PhotoListAdapterItem>) {
    val rangeStartPosition = this.items.size
    this.items.addAll(items)
    val count = items.size
    notifyItemRangeInserted(rangeStartPosition, count)
  }

  /**
   * Inserts an item that shows a progress into [items].
   */
  fun addLoadingItem() {
    val itemPosition = items.size
    items.add(LOADING_ITEM)
    notifyItemInserted(itemPosition)
  }

  /**
   * Removes an item with progress from the list.
   * It is assumed that the loading item is last one in [items],
   * so it is up to client of this class to know when to call this method.
   * It does not perform any checks.
   */
  fun removeLoadingItem() {
    items.removeAt(items.lastIndex)
    notifyItemRemoved(items.lastIndex)
  }
}