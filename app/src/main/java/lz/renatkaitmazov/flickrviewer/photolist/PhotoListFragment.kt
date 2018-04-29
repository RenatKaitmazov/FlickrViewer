package lz.renatkaitmazov.flickrviewer.photolist

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager.SavedState as GridLayoutState
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_photo_list.*
import lz.renatkaitmazov.data.rest.METHOD_RECENT
import lz.renatkaitmazov.data.rest.METHOD_SEARCH
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment
import lz.renatkaitmazov.flickrviewer.base.listener.DoubleTapDetector
import lz.renatkaitmazov.flickrviewer.base.showLongToast
import lz.renatkaitmazov.flickrviewer.photolist.adapter.DividerDecoration
import lz.renatkaitmazov.flickrviewer.base.listener.InfiniteBottomScroll
import lz.renatkaitmazov.flickrviewer.photodetail.PhotoDetailActivity
import lz.renatkaitmazov.flickrviewer.photolist.adapter.PhotoListAdapter
import lz.renatkaitmazov.flickrviewer.photolist.adapter.PhotoListItemAnimator
import lz.renatkaitmazov.flickrviewer.photolist.adapter.PhotoListThumbnailViewHolder
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListThumbnailItem
import lz.renatkaitmazov.flickrviewer.search.SearchActivity
import javax.inject.Inject

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListFragment
  : BaseFragment(),
  PhotoListFragmentView,
  SwipeRefreshLayout.OnRefreshListener,
  InfiniteBottomScroll.Callback,
  DoubleTapDetector.DoubleTapListener,
  PhotoListThumbnailViewHolder.OnThumbnailClickListener {

  companion object {
    /**
     * The user can view at most 10 pages.
     */
    private const val MAX_PAGE_LIMIT = 10

    /**
     * Number of items left for the user to reach the very end of the list.
     */
    private const val ITEMS_THRESHOLD = 6

    /**
     * A key for [currentPage].
     */
    private const val KEY_BUNDLE_PAGE = "KEY_BUNDLE_PAGE"

    /**
     * A key for [serverHasMoreData].
     */
    private const val KEY_BUNDLE_HAS_MORE_DATA = "KEY_BUNDLE_HAS_MORE_DATA"

    /**
     * A key for the scroll position of [photoListRecyclerView].
     */
    private const val KEY_BUNDLE_LAYOUT_STATE = "KEY_BUNDLE_LAYOUT_STATE"

    /**
     * A key for [flickrPhotoMethod].
     */
    private const val KEY_BUNDLE_FLICKR_METHOD = "KEY_BUNDLE_FLICKR_METHOD"

    /**
     * A key for [lastSearchQuery].
     */
    private const val KEY_BUNDLE_LAST_QUERY = "KEY_BUNDLE_LAST_QUERY"

    private const val REQUEST_CODE_SEARCH_ACTIVITY = 1_000

    /**
     * A handy factory method to create instances of this class.
     */
    fun newInstance(): PhotoListFragment = PhotoListFragment()
  }

  /**
   * Used for pagination.
   */
  private var currentPage = 1

  /**
   * A flag used for pagination to determine if the server has more data to return.
   */
  private var serverHasMoreData = true

  /**
   * A method that defines what kind of photos to return (recent, searchFirstPage, etc).
   */
  private var flickrPhotoMethod = METHOD_RECENT

  /**
   * Keeps track of the last searchFirstPage query.
   */
  private var lastSearchQuery = ""

  private lateinit var photoListAdapter: PhotoListAdapter
  private lateinit var gestureDetector: GestureDetectorCompat
  private val paginator = InfiniteBottomScroll(this)

  @Inject
  lateinit var presenter: IPhotoListFragmentPresenter

  /*------------------------------------------------------------------------*/
  /* Fragment Lifecycle Events                                              */
  /*------------------------------------------------------------------------*/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    photoListAdapter = PhotoListAdapter(app, this)
    if (savedInstanceState != null) {
      flickrPhotoMethod = savedInstanceState.getString(KEY_BUNDLE_FLICKR_METHOD, METHOD_RECENT)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initGestureDetector()
    initToolbar(savedInstanceState)
    initRefreshLayout()
    initRecyclerView(savedInstanceState)
    initNoConnectionErrorView()
    initNoResultsErrorView()
    presenter.getPhotoListAtFirstPage()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putInt(KEY_BUNDLE_PAGE, currentPage)
    outState.putBoolean(KEY_BUNDLE_HAS_MORE_DATA, serverHasMoreData)
    val layoutState = photoListRecyclerView.layoutManager.onSaveInstanceState()
    outState.putParcelable(KEY_BUNDLE_LAYOUT_STATE, layoutState)
    outState.putString(KEY_BUNDLE_FLICKR_METHOD, flickrPhotoMethod)
    outState.putString(KEY_BUNDLE_LAST_QUERY, lastSearchQuery)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      REQUEST_CODE_SEARCH_ACTIVITY -> {
        if (resultCode == RESULT_OK && data != null) {
          flickrPhotoMethod = METHOD_SEARCH
          lastSearchQuery = data.getStringExtra(Intent.EXTRA_TEXT)
          presenter.search(lastSearchQuery)
        }
      }
    }
  }

  override fun onDestroy() {
    presenter.unbind()
    super.onDestroy()
  }

  /*------------------------------------------------------------------------*/
  /* Parent Abstract Methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getViewResId() = R.layout.fragment_photo_list

  /*------------------------------------------------------------------------*/
  /* Menu                                                                   */
  /*------------------------------------------------------------------------*/

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_photo_list, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_item_search -> startSearchActivity()
      else -> super.onOptionsItemSelected(item)
    }
  }

  /*------------------------------------------------------------------------*/
  /* PhotoListFragmentView implementation                                   */
  /*------------------------------------------------------------------------*/

  override fun showProgress() {
    photoListRefreshLayout.isRefreshing = true
  }

  override fun hideProgress() {
    photoListRefreshLayout.isRefreshing = false
  }

  override fun showThumbnails(thumbnails: List<PhotoListAdapterItem>) {
    photoListAdapter.update(thumbnails)
  }

  override fun addLoadingItem() {
    photoListAdapter.addLoadingItem()
  }

  override fun removeLoadingItem() {
    photoListAdapter.removeLoadingItem()
  }

  override fun onFirstPageNetworkError() {
    if (photoListAdapter.isEmpty()) {
      noConnectionErrorView.show()
    } else {
      // Do not hide the current content if there is no Internet connection.
      showLongToast(R.string.error_no_connection)
    }
  }

  override fun onNextPageNetworkError() {
    // Need to decrement the current page because the attempt to load a new page was not
    // successful.
    --currentPage
    paginator.onNextPageLoaded()
    showLongToast(R.string.error_no_connection)
  }

  override fun onFirstPageEmptyResponseError() {
    serverHasMoreData = false
    noResultsErrorView.show()
  }

  override fun onNextPageEmptyResponseError() {
    serverHasMoreData = false
  }

  override fun hideAnyVisibleError() {
    noConnectionErrorView.hide()
    noResultsErrorView.hide()
  }

  override fun showNextPageThumbnails(thumbnails: List<PhotoListAdapterItem>) {
    photoListAdapter.append(thumbnails)
    paginator.onNextPageLoaded()
  }

  override fun resetState() {
    currentPage = 1
    serverHasMoreData = true
  }

  override fun restoreDefaultMethod() {
    flickrPhotoMethod = METHOD_RECENT
    lastSearchQuery = ""
  }

  override fun scrollToFirstItem() {
    val gridLayoutManager = photoListRecyclerView.layoutManager as GridLayoutManager
    // Determine how many items fit into the screen.
    val numberOfVisibleItems = gridLayoutManager.childCount
    val threshold = numberOfVisibleItems shl 1
    // Determine the position of the currently visible top item.
    val currentTopItemPosition = gridLayoutManager.findFirstVisibleItemPosition()
    if (currentTopItemPosition >= threshold) {
      // Too far away from the first item. Quickly scroll to the bottom of the initially visible
      // items.
      photoListRecyclerView.scrollToPosition(numberOfVisibleItems)
    }
    val firstItemPosition = 0
    // And then start smooth scroll to the very first item.
    photoListRecyclerView.smoothScrollToPosition(firstItemPosition)
  }

  override fun setSearchTitle(query: String) {
    val title = getString(R.string.title_search_with_query, query)
    toolbar.title = title
  }

  override fun setRecentTitle() {
    val title = getString(R.string.title_recent_photos)
    toolbar.title = title
  }

  /*------------------------------------------------------------------------*/
  /* SwipeRefreshLayout.OnRefreshListener implementation                    */
  /*------------------------------------------------------------------------*/

  override fun onRefresh() {
    presenter.updatePhotoList()
  }

  /*------------------------------------------------------------------------*/
  /* InfiniteBottomScroll.Callback implementation                           */
  /*------------------------------------------------------------------------*/

  override fun canLoadMore(): Boolean {
    val layoutManager = photoListRecyclerView.layoutManager as GridLayoutManager
    val totalItemCount = layoutManager.itemCount
    val lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
    val closeToBottom = ITEMS_THRESHOLD >= (totalItemCount - lastCompletelyVisibleItemPosition)
    val didNotExceedPageLimit = currentPage <= MAX_PAGE_LIMIT
    return didNotExceedPageLimit && closeToBottom && serverHasMoreData
  }

  override fun loadMode() {
    ++currentPage
    when (flickrPhotoMethod) {
      METHOD_RECENT -> presenter.getNextPage(currentPage)
      METHOD_SEARCH -> presenter.searchNextPage(lastSearchQuery, currentPage)
      else -> throw IllegalArgumentException("Unknown method")
    }
  }

  /*------------------------------------------------------------------------*/
  /* DoubleTapDetector.DoubleTapListener implementation                     */
  /*------------------------------------------------------------------------*/

  override fun onDoubleTaped(viewId: Int): Boolean {
    return when (viewId) {
      R.id.toolbar -> {
        presenter.onToolbarDoubleTap()
        true
      }
      else -> false
    }
  }

  /*------------------------------------------------------------------------*/
  /* PhotoListThumbnailViewHolder.OnThumbnailClickListener implementation   */
  /*------------------------------------------------------------------------*/

  override fun onThumbnailClicked(item: PhotoListThumbnailItem,
                                  sharedView: View) {
    val activityIntent = PhotoDetailActivity.newIntent(app, item.mediumSizeImageUrl)
    if (supportsMaterialDesign()) {
      val sharedElementTransition = ActivityOptionsCompat.makeSceneTransitionAnimation(
        activity!!,
        sharedView,
        sharedView.transitionName
      )
      startActivity(activityIntent, sharedElementTransition.toBundle())
    } else {
      startActivity(activityIntent)
    }
  }

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun initToolbar(savedInstanceState: Bundle?) {
    toolbar.setOnTouchListener(View.OnTouchListener { _, event ->
      return@OnTouchListener gestureDetector.onTouchEvent(event)
    })
    var title = getString(R.string.title_recent_photos)
    if (savedInstanceState != null) {
      val searchQuery = savedInstanceState.getString(KEY_BUNDLE_LAST_QUERY, "")
      if (searchQuery.isNotBlank()) {
        title = getString(R.string.title_search_with_query, searchQuery)
      }
    }
    toolbar.title = title
    val hostActivity = activity as? AppCompatActivity
    hostActivity?.setSupportActionBar(toolbar)
    if (supportsMaterialDesign()) {
      val toolbarElevation = resources.getDimension(R.dimen.elevation_4dp)
      toolbar.elevation = toolbarElevation
    }
  }

  private fun initRefreshLayout() {
    photoListRefreshLayout.setOnRefreshListener(this)
    photoListRefreshLayout.setColorSchemeResources(
      android.R.color.holo_red_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_blue_bright
    )
  }

  private fun initRecyclerView(savedInstanceState: Bundle?) {
    photoListRecyclerView.adapter = photoListAdapter
    photoListRecyclerView.setHasFixedSize(true)
    photoListRecyclerView.itemAnimator = PhotoListItemAnimator()
    val spanCount = resources.getInteger(R.integer.span_count)
    photoListRecyclerView.layoutManager = GridLayoutManager(app, spanCount)
    val clearance = resources.getDimension(R.dimen.clearance_image_item).toInt()
    val dividerDecoration = DividerDecoration(clearance, spanCount)
    photoListRecyclerView.addItemDecoration(dividerDecoration)
    photoListRecyclerView.addOnScrollListener(paginator)
    restoreRecyclerViewState(savedInstanceState)
  }

  private fun restoreRecyclerViewState(savedInstanceState: Bundle?) {
    if (savedInstanceState != null) {
      currentPage = savedInstanceState.getInt(KEY_BUNDLE_PAGE, 1)
      serverHasMoreData = savedInstanceState.getBoolean(KEY_BUNDLE_HAS_MORE_DATA, true)
      val layoutState = savedInstanceState.getParcelable(KEY_BUNDLE_LAYOUT_STATE) as GridLayoutState
      photoListRecyclerView.layoutManager.onRestoreInstanceState(layoutState)
    }
  }

  private fun initNoConnectionErrorView() {
    noConnectionErrorView.setOnErrorButtonClickListener(View.OnClickListener {
      presenter.updatePhotoList()
    })
  }

  private fun initNoResultsErrorView() {
    noResultsErrorView.setOnErrorButtonClickListener(View.OnClickListener {
      presenter.updatePhotoList()
    })
  }

  private fun initGestureDetector() {
    val doubleTapDetector = DoubleTapDetector(this, R.id.toolbar)
    gestureDetector = GestureDetectorCompat(app, doubleTapDetector)
  }

  private fun startSearchActivity(): Boolean {
    val hostActivity = activity ?: return false
    if (!isRecyclerViewIdle()) {
      photoListRecyclerView.stopScroll()
    }
    val activityIntent = SearchActivity.newIntent(hostActivity)
    startActivityForResult(activityIntent, REQUEST_CODE_SEARCH_ACTIVITY)
    return true
  }

  private fun isRecyclerViewIdle(): Boolean {
    return photoListRecyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
  }
}