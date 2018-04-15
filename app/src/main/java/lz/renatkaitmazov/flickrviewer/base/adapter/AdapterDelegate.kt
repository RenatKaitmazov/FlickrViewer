package lz.renatkaitmazov.flickrviewer.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Creates and binds view holders with data that should be shown on the screen in a
 * recycler view. It is expected that classes implementing this interface are used inside adapters
 * as delegates.
 *
 * @author Renat Kaitmazov
 */
interface AdapterDelegate<in T> {
  /**
   * Checks to see if the adapter is responsible for creating a view holders for the
   * given items.
   *
   * @param items data source of the adapter.
   * @param position of an item in the data source [items].
   * @return true if the adapter is responsible for creating a view holders for the [items],
   * false otherwise.
   */
  fun isForViewType(items: T, position: Int): Boolean

  /**
   * Creates an instance of [RecyclerView.ViewHolder].
   *
   * @param parent the view group which is the parent of this data source.
   */
  fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

  /**
   * Binds an items from [items] with [viewHolder] to show data to the user.
   *
   * @param items data source of the adapter.
   * @param position of an item is the data source which should be bound with [viewHolder].
   * @param viewHolder a holder that will display an item's data in [items] at [position].
   */
  fun onBindViewHolder(items: T, position: Int, viewHolder: RecyclerView.ViewHolder)
}