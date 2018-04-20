package lz.renatkaitmazov.flickrviewer.photolist

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import kotlinx.android.synthetic.main.fragment_photo_list.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment
import lz.renatkaitmazov.flickrviewer.base.showLongToast
import lz.renatkaitmazov.flickrviewer.photolist.adapter.DividerDecoration
import lz.renatkaitmazov.flickrviewer.photolist.adapter.InfiniteBottomScroll
import lz.renatkaitmazov.flickrviewer.photolist.adapter.PhotoListAdapter
import lz.renatkaitmazov.flickrviewer.photolist.adapter.PhotoListItemAnimator
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItem
import javax.inject.Inject

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListFragment
  : BaseFragment(),
  PhotoListFragmentView,
  SwipeRefreshLayout.OnRefreshListener,
  InfiniteBottomScroll.Callback {

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

  private lateinit var photoListAdapter: PhotoListAdapter

  private val paginator = InfiniteBottomScroll(this)

  @Inject
  lateinit var presenter: IPhotoListFragmentPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initToolbar()
    initRefreshLayout()
    initRecyclerView()
    initNoConnectionErrorView()
    initNoResultsErrorView()
    presenter.getPhotoListAtFirstPage()
  }

  override fun onDestroy() {
    presenter.unbind()
    super.onDestroy()
  }

  override fun getViewResId() = R.layout.fragment_photo_list

  private fun initToolbar() {
    if (supportsMaterialDesign()) {
      val toolbarElevation = resources.getDimension(R.dimen.elevation_4dp)
      toolbar.elevation = toolbarElevation
    }
    toolbar.setTitle(R.string.title_recent_photos)
    val hostActivity = activity as? AppCompatActivity
    hostActivity?.setSupportActionBar(toolbar)
  }

  private fun initRefreshLayout() {
    photoListRefreshLayout.setOnRefreshListener(this)
    photoListRefreshLayout.setColorSchemeResources(
      android.R.color.holo_red_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_blue_bright
    )
  }

  private fun initRecyclerView() {
    photoListAdapter = PhotoListAdapter(activity as Context)
    photoListRecyclerView.setHasFixedSize(true)
    photoListRecyclerView.adapter = photoListAdapter
    photoListRecyclerView.itemAnimator = PhotoListItemAnimator()
    val spanCount = resources.getInteger(R.integer.span_count)
    photoListRecyclerView.layoutManager = GridLayoutManager(activity, spanCount)
    val clearance = resources.getDimension(R.dimen.clearance_image_item).toInt()
    val dividerDecoration = DividerDecoration(clearance, spanCount)
    photoListRecyclerView.addItemDecoration(dividerDecoration)
    photoListRecyclerView.addOnScrollListener(paginator)
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

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_photo_list, menu)
  }

  override fun showProgress() {
    photoListRefreshLayout.isRefreshing = true
  }

  override fun hideProgress() {
    photoListRefreshLayout.isRefreshing = false
  }

  override fun onRefresh() {
    presenter.updatePhotoList()
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
    noConnectionErrorView.show()
  }

  override fun onNextPageNetworkError() {
    // Need to decrement the current page because the attempt to load a new page was not
    // successful.
    --currentPage
    showLongToast(R.string.error_when_loading)
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
    paginator.loadingCompleted()
  }

  override fun resetState() {
    currentPage = 1
    serverHasMoreData = true
  }

  override fun canLoadMore(): Boolean {
    val layoutManager = photoListRecyclerView.layoutManager as GridLayoutManager
    val totalItemCount = layoutManager.itemCount
    val lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
    val closeToBottom = ITEMS_THRESHOLD >= (totalItemCount - lastCompletelyVisibleItemPosition)
    val didNotExceedPageLimit = currentPage <= MAX_PAGE_LIMIT
    return didNotExceedPageLimit && closeToBottom && serverHasMoreData
  }

  override fun loadMode() {
    presenter.getNextPage(++currentPage)
  }
}