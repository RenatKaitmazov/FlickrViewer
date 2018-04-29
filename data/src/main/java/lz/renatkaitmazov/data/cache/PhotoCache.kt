package lz.renatkaitmazov.data.cache

import lz.renatkaitmazov.data.model.entity.PhotoEntity
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoCache : Cache<MutableList<PhotoEntity>> {

  /**
   * The underlying data structure that supports thread-safe operations.
   */
  private val cache = ConcurrentHashMap<String, MutableList<PhotoEntity>>()

  override fun get(key: String): MutableList<PhotoEntity>? {
    return cache[key]
  }

  override fun put(key: String, value: MutableList<PhotoEntity>) {
    cache[key] = value
  }

  override fun remove(key: String): Boolean {
    val removedValue = cache.remove(key)
    return removedValue != null
  }
}