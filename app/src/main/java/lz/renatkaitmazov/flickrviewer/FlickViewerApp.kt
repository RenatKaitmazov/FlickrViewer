package lz.renatkaitmazov.flickrviewer

import android.app.Activity
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import lz.renatkaitmazov.flickrviewer.di.component.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * A custom application class to perform necessary setups.
 *
 * @author Renat Kaitmazov
 */
class FlickViewerApp
  : Application(),
  HasActivityInjector {

  @Suppress("MemberVisibilityCanBePrivate")
  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  var referenceWatcher: RefWatcher? = null
    private set

  companion object {

    /**
     * Returns the current application instance.
     *
     * @param ctx see [Context].
     * @return current application instance.
     */
    fun get(ctx: Context): FlickViewerApp = ctx.applicationContext as FlickViewerApp
  }

  override fun onCreate() {
    super.onCreate()
    initDagger()
    if (BuildConfig.DEBUG) {
      initTimber()
      initLeakCanary()
    }
  }

  override fun activityInjector(): AndroidInjector<Activity> {
    return dispatchingAndroidInjector
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

  private fun initDagger() {
    DaggerAppComponent.builder()
      .bindContext(this)
      .bindApp(this)
      .build()
      .inject(this)
  }
}