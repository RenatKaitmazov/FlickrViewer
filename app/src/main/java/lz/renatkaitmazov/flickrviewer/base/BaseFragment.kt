package lz.renatkaitmazov.flickrviewer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection
import lz.renatkaitmazov.flickrviewer.FlickViewerApp
import javax.inject.Inject

/**
 * Extend this class if you want your fragment to be retained on
 * configuration changes.
 *
 * @author Renat Kaitmazov
 */
abstract class BaseFragment : Fragment() {

  @Inject
  lateinit var app: FlickViewerApp

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidSupportInjection.inject(this)
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onDestroy() {
    super.onDestroy()
    enableMemoryLeakWatcher()
  }

  private fun enableMemoryLeakWatcher() {
    app.referenceWatcher?.watch(this)
  }
}