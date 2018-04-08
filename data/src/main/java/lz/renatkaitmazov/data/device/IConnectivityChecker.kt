package lz.renatkaitmazov.data.device

import io.reactivex.Single

/**
 *
 * @author Renat Kaitmazov
 */
interface IConnectivityChecker {

  /**
   * Checks to see if there is an Internet connection.
   *
   * @return true if there is a connection, be it metered or unmetered,
   * false otherwise.
   */
  fun hasInternetConnection(): Boolean
}