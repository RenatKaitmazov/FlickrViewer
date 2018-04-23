package lz.renatkaitmazov.flickrviewer.base

import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
  protected lateinit var app: FlickViewerApp

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidSupportInjection.inject(this)
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(getViewResId(), container, false)
  }

  override fun onDestroy() {
    super.onDestroy()
    enableMemoryLeakWatcher()
  }

  @LayoutRes
  protected abstract fun getViewResId(): Int

  protected fun supportsMaterialDesign(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
  }

  private fun enableMemoryLeakWatcher() {
    app.referenceWatcher?.watch(this)
  }
}