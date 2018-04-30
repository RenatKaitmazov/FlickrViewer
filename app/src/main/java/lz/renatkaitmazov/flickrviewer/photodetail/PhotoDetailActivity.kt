package lz.renatkaitmazov.flickrviewer.photodetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseActivity
import lz.renatkaitmazov.flickrviewer.base.supportsMaterialDesign

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoDetailActivity : BaseActivity() {

  companion object {
    /**
     * An empty string.
     */
    private const val EMPTY_STRING = ""

    /**
     * A key associated with a photo's URL we want to show.
     */
    private const val KEY_EXTRA_PHOTO_URL = "KEY_EXTRA_PHOTO_URL"

    /**
     * A handy method to obtain an intent to start this activity.
     *
     * @param context is needed to start this activity. Only those objects that have an access
     *                to the context can start this activity.
     * @param photoUrl  a photo's URL from where the actual image will be downloaded and shown
     *                  to the user.
     */
    fun newIntent(context: Context, photoUrl: String): Intent {
      return Intent(context, PhotoDetailActivity::class.java)
        .putExtra(KEY_EXTRA_PHOTO_URL, photoUrl)
    }
  }

  /*------------------------------------------------------------------------*/
  /* Activity Lifecycle Events                                              */
  /*------------------------------------------------------------------------*/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (supportsMaterialDesign()) {
      // Exclude bottom navigation bar from shared element transition to avoid
      // flickering while animating.
      val transition = window.enterTransition
      transition.excludeTarget(android.R.id.navigationBarBackground, true)
      val duration = resources.getInteger(R.integer.anim_duration_very_short).toLong()
      transition.duration = duration
    }
  }

  /*------------------------------------------------------------------------*/
  /* Parent Abstract methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getFragment(): Fragment {
    val photoUrl = intent.getStringExtra(KEY_EXTRA_PHOTO_URL) ?: EMPTY_STRING
    return PhotoDetailFragment.newInstance(photoUrl)
  }
}