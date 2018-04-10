package lz.renatkaitmazov.flickrviewer

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

/**
 * A custom application class to perform necessary setups.
 *
 * @author Renat Kaitmazov
 */
class FlickViewerApp : Application() {

  var referenceWatcher: RefWatcher? = null
    private set

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      initTimber()
      initLeakCanary()
    }
  }

  private fun initLeakCanary() {
    if (!LeakCanary.isInAnalyzerProcess(this)) {
      referenceWatcher = LeakCanary.install(this)
    }
  }

  private fun initTimber() {
    val tree = Timber.DebugTree()
    Timber.plant(tree)
  }
}