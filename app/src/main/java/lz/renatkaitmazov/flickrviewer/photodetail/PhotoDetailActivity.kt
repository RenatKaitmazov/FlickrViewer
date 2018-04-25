package lz.renatkaitmazov.flickrviewer.photodetail

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import lz.renatkaitmazov.flickrviewer.base.BaseActivity

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
  /* Parent Abstract methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getFragment(): Fragment {
    val photoUrl = intent.getStringExtra(KEY_EXTRA_PHOTO_URL) ?: EMPTY_STRING
    return PhotoDetailFragment.newInstance(photoUrl)
  }
}