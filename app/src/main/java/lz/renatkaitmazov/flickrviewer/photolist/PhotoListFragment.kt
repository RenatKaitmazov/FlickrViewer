package lz.renatkaitmazov.flickrviewer.photolist

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_photo_list.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment
import lz.renatkaitmazov.flickrviewer.photolist.adapter.DividerDecoration
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
  SwipeRefreshLayout.OnRefreshListener {

  companion object {
    /**
     * Uniquely identifies this fragment.
     */
    const val ID = "PhotoListFragment"

    /**
     * A handy factory method to create instances of this class.
     */
    fun newInstance(): PhotoListFragment = PhotoListFragment()
  }

  /**
   * Used for pagination.
   */
  private var currentPage = 1

  private lateinit var photoListAdapter: PhotoListAdapter

  @Inject
  lateinit var presenter: IPhotoListFragmentPresenter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initToolbar()
    initRefreshLayout()
    initRecyclerView()
    presenter.getPhotoList(currentPage)
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
    photoListRecyclerView.layoutManager = GridLayoutManager(app, spanCount)
    val clearance = resources.getDimension(R.dimen.clearance_image_item).toInt()
    val dividerDecoration = DividerDecoration(clearance, spanCount)
    photoListRecyclerView.addItemDecoration(dividerDecoration)
  }

  override fun showProgress() {
    photoListRefreshLayout.isRefreshing = true
  }

  override fun hideProgress() {
    photoListRefreshLayout.isRefreshing = false
  }

  override fun onRefresh() {
    resetState()
    presenter.updatePhotoList()
  }

  override fun showThumbnails(thumbnails: List<PhotoListAdapterItem>) {
    photoListAdapter.update(thumbnails)
  }

  private fun resetState() {
    currentPage = 1
  }
}