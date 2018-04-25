package lz.renatkaitmazov.flickrviewer.base

import android.os.Build
import lz.renatkaitmazov.flickrviewer.BuildConfig

/**
 * This file contains global functions.
 *
 * @author Renat Kaitmazov
 */

/**
 * Checks to see if the device runs on Lollipop or higher.
 */
fun supportsMaterialDesign(): Boolean {
  return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}