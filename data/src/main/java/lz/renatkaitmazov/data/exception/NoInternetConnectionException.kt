package lz.renatkaitmazov.data.exception

/**
 *
 * @author Renat Kaitmazov
 */
class NoInternetConnectionException : Exception {
  constructor()
  constructor(msg: String) : super(msg)
}