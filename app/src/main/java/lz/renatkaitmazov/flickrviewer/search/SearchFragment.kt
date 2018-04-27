package lz.renatkaitmazov.flickrviewer.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.fragment_search.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment
import lz.renatkaitmazov.flickrviewer.base.showLongToast
import javax.inject.Inject

/**
 *
 * @author Renat Kaitmazov
 */
class SearchFragment
  : BaseFragment(),
  SearchFragmentView {

  companion object {

    /**
     * A handy factory method to create instances of this class.
     */
    fun newInstance() = SearchFragment()
  }

  @Inject
  lateinit var presenter: ISearchFragmentPresenter

  /*------------------------------------------------------------------------*/
  /* Fragment Lifecycle Events                                              */
  /*------------------------------------------------------------------------*/

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initToolbar()
    initSearchEditText()
  }

  /*------------------------------------------------------------------------*/
  /* Parent Abstract methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getViewResId() = R.layout.fragment_search

  /*------------------------------------------------------------------------*/
  /* SearchFragmentView implementation                                      */
  /*------------------------------------------------------------------------*/
  override fun showInvalidQueryLength() {
    showLongToast(R.string.error_short_query)
  }

  override fun onQueryValidated(query: String) {
    val hostActivity = activity
    if (hostActivity != null) {
      val queryIntent = Intent()
        .putExtra(Intent.EXTRA_TEXT, query)
      hostActivity.setResult(RESULT_OK, queryIntent)
      hostActivity.finish()
    }
  }

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun initToolbar() {
    val toolbar = includedToolbar as Toolbar
    val title = getString(R.string.title_search)
    toolbar.title = title
    if (supportsMaterialDesign()) {
      val elevation = resources.getDimension(R.dimen.elevation_4dp)
      toolbar.elevation = elevation
    }
    val hostActivity = activity as? AppCompatActivity
    hostActivity?.setSupportActionBar(toolbar)
  }

  private fun initSearchEditText() {
    searchTextInputEditText.setOnEditorActionListener { _, actionId, _ ->
      return@setOnEditorActionListener when (actionId) {
        EditorInfo.IME_ACTION_DONE -> {
          val query = searchTextInputEditText.text.toString()
          presenter.onValidateSearchQuery(query)
          true
        }
        else -> false
      }
    }
  }
}