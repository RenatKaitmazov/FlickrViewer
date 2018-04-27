package lz.renatkaitmazov.flickrviewer.search

import lz.renatkaitmazov.flickrviewer.base.BaseView

/**
 *
 * @author Renat Kaitmazov
 */
interface SearchFragmentView : BaseView {
  /**
   * Shows an error that a search query is too short.
   */
  fun showInvalidQueryLength()

  /**
   * The search query is validated and it is correct.
   */
  fun onQueryValidated(query: String)
}