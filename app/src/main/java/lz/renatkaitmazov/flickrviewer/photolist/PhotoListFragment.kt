package lz.renatkaitmazov.flickrviewer.photolist

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.fragment_photo_list.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment
import lz.renatkaitmazov.flickrviewer.photolist.adapter.PhotoListAdapter
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

  @Inject
  lateinit var presenter: IPhotoListFragmentPresenter

  override fun getViewResId() = R.layout.fragment_photo_list

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

  private fun initToolbar() {
    val toolbar = includedToolbar as Toolbar
    if (supportsMaterialDesign()) {
      val toolbarElevation = resources.getDimension(R.dimen.elevation_4dp)
      toolbar.elevation = toolbarElevation
    }
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
    photoListRecyclerView.setHasFixedSize(true)
    photoListRecyclerView.adapter = PhotoListAdapter()
    photoListRecyclerView.layoutManager = GridLayoutManager(app, 3)
  }

  override fun showProgress() {
    photoListRefreshLayout.isRefreshing = true
  }

  override fun hideProgress() {
    photoListRefreshLayout.isRefreshing = false
  }

  override fun onRefresh() {

  }
}