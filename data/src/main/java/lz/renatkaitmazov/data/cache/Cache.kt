package lz.renatkaitmazov.data.cache

/**
 * Implementations of this interface must be thread-safe.
 * The key is always a string.
 *
 * @author Renat Kaitmazov
 */
interface Cache<V> {
  /**
   * Returns a value associated with [key].
   *
   * @param key whose corresponding value we want to get.
   * @return a value associated with [key] if the value is present in the cache,
   * null otherwise.
   */
  fun get(key: String): V?

  /**
   * Associates [value] with [key] and stores it in the cache.
   *
   * @param key with which [value] is associated.
   * @param value which is stored in the cache by the given [key].
   */
  fun put(key: String, value: V)

  /**
   * Removes a value associated with [key].
   *
   * @param key whose associated value will be removed from the cache.
   * @return true if the value is present in the cache and has been successfully removed,
   * false otherwise.
   */
  fun remove(key: String): Boolean
}