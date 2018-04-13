package lz.renatkaitmazov.flickrviewer.photolist

import lz.renatkaitmazov.flickrviewer.base.BaseActivity

class PhotoListActivity : BaseActivity() {

  override fun getFragment() = PhotoListFragment.newInstance()
}
