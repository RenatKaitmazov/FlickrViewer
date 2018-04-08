package lz.renatkaitmazov.data.exception

/**
 *
 * @author Renat Kaitmazov
 */
class NoConnectionException : Exception {
  constructor()
  constructor(msg: String) : super(msg)
}