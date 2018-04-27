package lz.renatkaitmazov.flickrviewer.search

import lz.renatkaitmazov.flickrviewer.base.Presenter

/**
 *
 * @author Renat Kaitmazov
 */
interface ISearchFragmentPresenter : Presenter<SearchFragmentView> {

  /**
   * This method is called when the user enters a search query and clicks "Done" button
   * on the keyboard. The query must be at least 3 characters long.
   *
   * @param query a search query
   */
  fun onValidateSearchQuery(query: String)
}