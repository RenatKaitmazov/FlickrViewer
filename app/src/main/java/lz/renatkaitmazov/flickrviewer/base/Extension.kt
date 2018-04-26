package lz.renatkaitmazov.flickrviewer.base

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * This file contains convenient extensions.
 *
 * @author Renat Kaitmazov
 */


/**
 * Shows a toast that displays the given string located in the app resources.
 *
 * @param stringResId the id of the string to be shown in the string resource file.
 */
fun Fragment.showLongToast(@StringRes stringResId: Int) {
  val text = getString(stringResId)
  showLongToast(text)
}

/**
 * Shows a toast that displays the given text.
 *
 * @param text to be displayed inside a toast.
 */
fun Fragment.showLongToast(text: String) {
  Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
}

/**
 * Makes the view visible.
 */
fun View.makeVisible() {
  visibility = View.VISIBLE
}

/**
 * Makes the view invisible and removes itself from the parent's hierarchy.
 */
fun View.makeGone() {
  visibility = View.GONE
}