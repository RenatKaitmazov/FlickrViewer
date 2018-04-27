package lz.renatkaitmazov.flickrviewer.search

import io.reactivex.disposables.CompositeDisposable
import lz.renatkaitmazov.flickrviewer.base.BasePresenter

/**
 *
 * @author Renat Kaitmazov
 */
class SearchFragmentPresenter(
  view: SearchFragmentView,
  subscriptionManager: CompositeDisposable
) : BasePresenter<SearchFragmentView>(view, subscriptionManager),
  ISearchFragmentPresenter {

  companion object {
    /**
     * The amount of minimum characters for a query.
     */
    private const val MIN_CHARACTER_COUNT = 3
  }

  override fun onValidateSearchQuery(query: String) {
    // Empty queries are nor allowed. Make sure that the user entered at least 3 characters.
    val trimmedQuery = query.trim()
    if (trimmedQuery.length < MIN_CHARACTER_COUNT) {
      view?.showInvalidQueryLength()
      return
    }
    view?.onQueryValidated(query)
  }
}