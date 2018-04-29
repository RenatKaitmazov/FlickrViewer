package lz.renatkaitmazov.flickrviewer.search

import lz.renatkaitmazov.flickrviewer.base.BaseView

/**
 *
 * @author Renat Kaitmazov
 */
interface SearchFragmentView : BaseView {
  /**
   * Shows an error that a searchFirstPage query is too short.
   */
  fun showInvalidQueryLength()

  /**
   * The searchFirstPage query is validated and it is correct.
   */
  fun onQueryValidated(query: String)
}