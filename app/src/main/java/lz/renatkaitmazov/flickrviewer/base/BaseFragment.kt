package lz.renatkaitmazov.flickrviewer.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Extend this class if you want your fragment to be retained on
 * configuration changes.
 *
 * @author Renat Kaitmazov
 */
abstract class BaseFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }
}