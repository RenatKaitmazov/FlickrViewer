package lz.renatkaitmazov.flickrviewer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import lz.renatkaitmazov.flickrviewer.R
import javax.inject.Inject

/**
 * Extends this class if your activity is an injection target
 * for Dagger.
 *
 * @author Renat Kaitmazov
 */
abstract class BaseActivity
  : AppCompatActivity(),
  HasSupportFragmentInjector {

  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_base)
    val fragmentManager = supportFragmentManager
    val containerId = R.id.fragmentContainer
    val currentFragment = fragmentManager.findFragmentById(containerId)
    if (currentFragment == null) {
      val newFragment = getFragment()
      fragmentManager.beginTransaction()
        .add(containerId, newFragment)
        .commitNow()
    }
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return fragmentInjector
  }

  /**
   * Forces child activities to return a fragment that will be added into the
   * fragment manager.
   */
  protected abstract fun getFragment(): Fragment
}