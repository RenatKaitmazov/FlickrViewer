package lz.renatkaitmazov.data.cache

import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @author Renat Kaitmazov
 */
class RecentPhotoCache : Cache<MutableList<RecentPhotoEntity>> {

  /**
   * The underlying data structure that supports thread-safe operations.
   */
  private val cache = ConcurrentHashMap<String, MutableList<RecentPhotoEntity>>()

  override fun get(key: String): MutableList<RecentPhotoEntity>? {
    return cache[key]
  }

  override fun put(key: String, value: MutableList<RecentPhotoEntity>) {
    cache[key] = value
  }

  override fun remove(key: String): Boolean {
    val removedValue = cache.remove(key)
    return removedValue != null
  }
}