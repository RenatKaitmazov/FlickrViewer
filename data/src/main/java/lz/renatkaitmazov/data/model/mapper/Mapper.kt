package lz.renatkaitmazov.data.model.mapper

/**
 * Converts one data type into another.
 *
 * @author Renat Kaitmazov
 */
interface Mapper<in From, out To> {
  /**
   * Converts an object of type [From] into an object of type [To].
   *
   * @param source the item being converted into type [To].
   * @return converted object of type [To].
   */
  fun map(source: From): To
}