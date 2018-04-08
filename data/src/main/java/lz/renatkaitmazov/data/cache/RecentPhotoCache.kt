package lz.renatkaitmazov.data.cache

import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @author Renat Kaitmazov
 */
class RecentPhotoCache : Cache<List<RecentPhotoEntity>> {

  /**
   * The underlying data structure that supports thread-safe operations.
   */
  private val cache = ConcurrentHashMap<String, List<RecentPhotoEntity>>()

  override fun get(key: String): List<RecentPhotoEntity>? {
    return cache[key]
  }

  override fun put(key: String, value: List<RecentPhotoEntity>) {
    cache[key] = value
  }

  override fun remove(key: String): Boolean {
    val removedValue = cache.remove(key)
    return removedValue != null
  }
}