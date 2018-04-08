package lz.renatkaitmazov.data.device

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager

/**
 *
 * @author Renat Kaitmazov
 */
class ConnectivityChecker(private val context: Context) : IConnectivityChecker {

  override fun hasInternetConnection(): Boolean {
    val connectivityManager = context.getConnectivityManager()
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }
}

private fun Context.getConnectivityManager(): ConnectivityManager {
  return getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
}