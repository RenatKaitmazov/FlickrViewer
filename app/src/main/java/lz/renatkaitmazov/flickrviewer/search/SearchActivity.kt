package lz.renatkaitmazov.flickrviewer.search

import android.content.Context
import android.content.Intent
import lz.renatkaitmazov.flickrviewer.base.BaseActivity

/**
 *
 * @author Renat Kaitmazov
 */
class SearchActivity : BaseActivity() {

  companion object {

    /**
     * A handy method that creates an intent to launch this activity.
     */
    fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
  }

  /*------------------------------------------------------------------------*/
  /* Parent Abstract methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getFragment() = SearchFragment.newInstance()
}